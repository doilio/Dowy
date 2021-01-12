package com.dowy.android.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dowy.android.adapter.categories.nowplaying.NowPlayingAdapter
import com.dowy.android.adapter.categories.popular.PopularAdapter
import com.dowy.android.adapter.categories.toprated.TopRatedAdapter
import com.dowy.android.adapter.categories.upcoming.UpcomingAdapter
import com.dowy.android.databinding.FragmentMainMoviesBinding
import com.dowy.android.utils.DEFAULT_LANGUAGE
import com.dowy.android.utils.LANGUAGE_KEY
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainMoviesFragment : Fragment() {

    private val viewModel: MainMoviesViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentMainMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchMovies()
    }

    private fun fetchMovies() {
        val language = sharedPreferences.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE)

        binding.recyclerPopularMovies.hasFixedSize()
        binding.recyclerUpcomingMovies.hasFixedSize()
        binding.recyclerNowPlayingMovies.hasFixedSize()
        binding.recyclerTopRatedMovies.hasFixedSize()

        binding.recyclerPopularMovies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerUpcomingMovies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerNowPlayingMovies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerTopRatedMovies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val popularAdapter = PopularAdapter()
        val upcomingAdapter = UpcomingAdapter()
        val nowPlayingAdapter = NowPlayingAdapter()
        val topRatedAdapter = TopRatedAdapter()
        viewModel.getPopular(language).observe(viewLifecycleOwner, { popularMoviesList ->
            popularAdapter.submitPopularMovie(popularMoviesList)
        })
        viewModel.getNowPlaying(language).observe(viewLifecycleOwner, { nowPlayingList ->
            nowPlayingAdapter.submitNowPlayingMovie(nowPlayingList)
        })
        viewModel.getTopRated(language).observe(viewLifecycleOwner, { topRatedList ->
            topRatedAdapter.submitTopRatedMovie(topRatedList)
        })
        viewModel.getUpcoming(language).observe(viewLifecycleOwner, { upcomingList ->
            upcomingAdapter.submitUpcomingMovie(upcomingList)
        })

        binding.recyclerPopularMovies.adapter = popularAdapter
        binding.recyclerUpcomingMovies.adapter = upcomingAdapter
        binding.recyclerNowPlayingMovies.adapter = nowPlayingAdapter
        binding.recyclerTopRatedMovies.adapter = topRatedAdapter
    }

}