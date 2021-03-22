package com.dowy.android.ui.movies

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.dowy.android.R
import com.dowy.android.adapter.movie.MovieAdapter
import com.dowy.android.adapter.movie.MovieClickListener
import com.dowy.android.adapter.loadstate.LoadStateAdapter
import com.dowy.android.databinding.FragmentMoviesBinding
import com.dowy.android.ui.settings.SettingsActivity
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
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private val viewModel: MoviesViewModel by viewModels()
    private var interstitialAd: InterstitialAd? = null
    private lateinit var adapter: MovieAdapter

    @Inject
    lateinit var sharedPreference: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        setupActionBar()
        return binding.root
    }

    private fun setupActionBar() {
        val title = when (sharedPreference.getString(MOVIE_KEY, DEFAULT_CATEGORY)) {
            "popular" -> getString(R.string.popular)
            "now_playing" -> getString(R.string.now_playing)
            "upcoming" -> getString(R.string.upcoming)
            "top_rated" -> getString(R.string.top_rated)
            else -> getString(R.string.movies)
        }
        ((activity as AppCompatActivity).supportActionBar)?.title = title
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()

        fetchMovies()

    }

    private fun fetchMovies() {

        lifecycleScope.launch {
            viewModel.getMoviesList().collectLatest {
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
        InterstitialAd.load(requireContext(),
            AD1_ID,
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
        adapter = MovieAdapter(
            MovieClickListener {

                // If Ad is ready to be displayed, then display it
                if (interstitialAd != null) {
                    interstitialAd?.show(requireActivity())
                } else {
                    Timber.d("Interstitial Ad not ready yet")
                }

                findNavController().navigate(
                    MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(
                        it
                    )
                )
            }).apply {
            addLoadStateListener { loadState ->
                // If list has items. Show
                binding.movieList.isVisible = loadState.source.refresh is LoadState.NotLoading
                // If loading or refreshing show spinner
                binding.movieProgress.isVisible = loadState.source.refresh is LoadState.Loading
                // If initial load fails show Retry button and text
                binding.buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                binding.moviesError.isVisible = loadState.source.refresh is LoadState.Error
            }
        }

        binding.movieList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter { adapter.retry() },
            footer = LoadStateAdapter { adapter.retry() }
        )

        // RecyclerView
        binding.movieList.hasFixedSize()
        val layoutManager = GridLayoutManager(activity, resources.getInteger(R.integer.span_count))
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = adapter.getItemViewType(position)
                return if (viewType == LOADSTATE_VIEW_TYPE) 1
                else resources.getInteger(R.integer.span_count)
            }

        }
        binding.movieList.layoutManager = layoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

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
                //searchView.setQuery("", false)
                searchItem.collapseActionView()
                query?.let {
                    queryMovieList(it)
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
                fetchMovies()
                return true
            }
        }

        val actionMenuItem = menu.findItem(R.id.ic_search)
        actionMenuItem.setOnActionExpandListener(expandListener)
    }

    private fun queryMovieList(movieQuery: String) {
        lifecycleScope.launch {
            viewModel.queryMovieList(movieQuery).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settingsActivity -> openSettings()
        }
        return NavigationUI.onNavDestinationSelected(
            item,
            findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    private fun openSettings() {
        startActivity(Intent(activity, SettingsActivity::class.java))
    }
}