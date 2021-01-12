package com.dowy.android.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dowy.android.R
import com.dowy.android.utils.DEFAULT_LANGUAGE
import com.dowy.android.utils.LANGUAGE_KEY
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class MainMoviesFragment : Fragment() {

    private val viewModel: MainMoviesViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchMovies()
    }

    private fun fetchMovies() {
        val language = sharedPreferences.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE)

        viewModel.getPopular(language).observe(viewLifecycleOwner, { popularMovies ->
            Timber.d(popularMovies[0].title)
        })
        viewModel.getNowPlaying(language).observe(viewLifecycleOwner, { nowPlaying ->
            Timber.d(nowPlaying[0].title)
        })
        viewModel.getTopRated(language).observe(viewLifecycleOwner, { topRated ->
            Timber.d(topRated[0].title)
        })
        viewModel.getUpcoming(language).observe(viewLifecycleOwner, { upcoming ->
            Timber.d(upcoming[0].title)
        })
    }

}