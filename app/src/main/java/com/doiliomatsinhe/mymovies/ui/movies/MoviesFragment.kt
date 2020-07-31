package com.doiliomatsinhe.mymovies.ui.movies

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.doiliomatsinhe.mymovies.R
import com.doiliomatsinhe.mymovies.adapter.MovieAdapter
import com.doiliomatsinhe.mymovies.adapter.MovieClickListener
import com.doiliomatsinhe.mymovies.adapter.LoadStateAdapter
import com.doiliomatsinhe.mymovies.data.Repository
import com.doiliomatsinhe.mymovies.databinding.FragmentMoviesBinding
import com.doiliomatsinhe.mymovies.ui.settings.SettingsActivity
import com.doiliomatsinhe.mymovies.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapter: MovieAdapter

    @Inject
    lateinit var sharedPreference: SharedPreferences

    @Inject
    lateinit var repository: Repository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()

        lifecycleScope.launch {
            viewModel.getMoviesList().collectLatest {
                adapter.submitData(it)
            }
        }

    }

    private fun initComponents() {
        setHasOptionsMenu(true)

        val category = sharedPreference.getString(CATEGORY_KEY, DEFAULT_CATEGORY)
        val language = sharedPreference.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE)

        val factory = MoviesViewModelFactory(repository, category, language)
        viewModel = ViewModelProvider(this, factory).get(MoviesViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner

        initAdapter()
        binding.buttonRetry.setOnClickListener { adapter.retry() }

    }

    private fun initAdapter() {
        adapter = MovieAdapter(MovieClickListener {
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
        inflater.inflate(R.menu.main_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settingsActivity -> openSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openSettings() {
        startActivity(Intent(activity, SettingsActivity::class.java))
    }
}