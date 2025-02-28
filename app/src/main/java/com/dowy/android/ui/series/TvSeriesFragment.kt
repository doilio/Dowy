package com.dowy.android.ui.series

import android.app.SearchManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.dowy.android.R
import com.dowy.android.adapter.loadstate.LoadStateAdapter
import com.dowy.android.adapter.series.SeriesAdapter
import com.dowy.android.adapter.series.SeriesClickListener
import com.dowy.android.databinding.FragmentTvSeriesBinding
import com.dowy.android.utils.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TvSeriesFragment : Fragment() {

    private lateinit var binding: FragmentTvSeriesBinding
    private val viewModel: TvSeriesViewModel by viewModels()
    private var interstitialAd: InterstitialAd? = null
    private lateinit var adapter: SeriesAdapter

    @Inject
    lateinit var sharedPreference: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTvSeriesBinding.inflate(inflater, container, false)
        setupActionBar()
        return binding.root
    }

    private fun setupActionBar() {
        val title = when (sharedPreference.getString(TV_KEY, DEFAULT_CATEGORY)) {
            "popular" -> getString(R.string.popular)
            "airing_today" -> getString(R.string.airing_today)
            "on_the_air" -> getString(R.string.on_the_air)
            "top_rated" -> getString(R.string.top_rated)
            else -> getString(R.string.tv_series)
        }
        ((activity as AppCompatActivity).supportActionBar)?.title = title
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()

        fetchSeries()
    }

    private fun fetchSeries() {
        lifecycleScope.launch {
            viewModel.getTvSeriesList().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initComponents() {
        setHasOptionsMenu(true)

        binding.lifecycleOwner = viewLifecycleOwner

        initAdapter()

        binding.buttonRetry.setOnClickListener { adapter.retry() }

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            requireContext(),
            AD2_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(myAd: InterstitialAd) {
                    Timber.d("Ad Loaded")
                    interstitialAd = myAd
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Timber.d("Failed to load ad: ${adError.message}")
                    interstitialAd = null
                }
            })
    }

    private fun initAdapter() {
        adapter = SeriesAdapter(
            SeriesClickListener {
                // If Ad is ready to be displayed, then display it
                if (interstitialAd != null) {
                    interstitialAd?.show(requireActivity())
                } else {
                    Timber.d("Interstitial Ad not ready yet")
                }

                findNavController().navigate(
                    TvSeriesFragmentDirections.actionTvSeriesFragmentToTvSeriesDetailsFragment(
                        it
                    )
                )
            }).apply {

            addLoadStateListener { loadState ->
                binding.seriesList.isVisible = loadState.source.refresh is LoadState.NotLoading

                binding.seriesProgress.isVisible = loadState.source.refresh is LoadState.Loading

                binding.seriesError.isVisible = loadState.source.refresh is LoadState.Error
                binding.buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
            }

        }

        binding.seriesList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter { adapter.retry() },
            footer = LoadStateAdapter { adapter.retry() }
        )


        // RecyclerView
        binding.seriesList.hasFixedSize()
        val layoutManager = GridLayoutManager(activity, resources.getInteger(R.integer.span_count))
        binding.seriesList.layoutManager = layoutManager
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = adapter.getItemViewType(position)
                return if (viewType == LOADSTATE_VIEW_TYPE) 1
                else resources.getInteger(R.integer.span_count)
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        menu.findItem(R.id.aboutFragment).isVisible = false
        menu.findItem(R.id.settingsActivity).isVisible = false

        implementSearch(menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun implementSearch(menu: Menu) {
        val manager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.ic_search)
        val searchView = searchItem.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE

        searchView.setSearchableInfo(manager.getSearchableInfo(requireActivity().componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchItem.collapseActionView()
                query?.let {
                    querySeriesList(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })


        val expandListener = object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                fetchSeries()
                return true
            }
        }

        val actionMenuItem = menu.findItem(R.id.ic_search)
        actionMenuItem.setOnActionExpandListener(expandListener)
    }

    private fun querySeriesList(seriesQuery: String) {
        lifecycleScope.launch {
            viewModel.querySeriesList(seriesQuery).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}