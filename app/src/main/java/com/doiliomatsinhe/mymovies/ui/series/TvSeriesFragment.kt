package com.doiliomatsinhe.mymovies.ui.series

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.doiliomatsinhe.mymovies.R
import com.doiliomatsinhe.mymovies.adapter.LoadStateAdapter
import com.doiliomatsinhe.mymovies.adapter.SeriesAdapter
import com.doiliomatsinhe.mymovies.adapter.SeriesClickListener
import com.doiliomatsinhe.mymovies.data.Repository
import com.doiliomatsinhe.mymovies.databinding.FragmentTvSeriesBinding
import com.doiliomatsinhe.mymovies.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TvSeriesFragment : Fragment() {

    private lateinit var binding: FragmentTvSeriesBinding
    private lateinit var viewModel: TvSeriesViewModel
    private lateinit var adapter: SeriesAdapter

    @Inject
    lateinit var sharedPreference: SharedPreferences

    @Inject
    lateinit var repository: Repository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentTvSeriesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()

        lifecycleScope.launch {
            viewModel.getTvSeriesList().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initComponents() {
        val category = sharedPreference.getString(CATEGORY_KEY, DEFAULT_CATEGORY)
        val language = sharedPreference.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE)

        val factory = TvSeriesViewModelFactory(repository, category, language)
        viewModel = ViewModelProvider(this, factory).get(TvSeriesViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner

        initAdapter()

        binding.buttonRetry.setOnClickListener { adapter.retry() }
    }

    private fun initAdapter() {
        adapter = SeriesAdapter(SeriesClickListener {
            Toast.makeText(activity, "${it.name} clicked!", Toast.LENGTH_SHORT).show()
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
}