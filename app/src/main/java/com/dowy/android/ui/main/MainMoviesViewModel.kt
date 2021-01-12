package com.dowy.android.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dowy.android.data.Repository
import com.dowy.android.model.movie.Movie
import com.dowy.android.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainMoviesViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun getPopular(language: String?): LiveData<List<Movie>> {
        val movie = MutableLiveData<List<Movie>>()

        uiScope.launch {
            movie.value = when (val myMovie = repository.getPopularMovies(language)) {
                is Result.Success -> myMovie.data
                is Result.Error -> null
            }
        }
        return movie
    }

    fun getTopRated(language: String?): LiveData<List<Movie>> {
        val movie = MutableLiveData<List<Movie>>()

        uiScope.launch {
            movie.value = when (val myMovie = repository.getTopRated(language)) {
                is Result.Success -> myMovie.data
                is Result.Error -> null
            }
        }
        return movie
    }

    fun getUpcoming(language: String?): LiveData<List<Movie>> {
        val movie = MutableLiveData<List<Movie>>()

        uiScope.launch {
            movie.value = when (val myMovie = repository.getUpcomingMovies(language)) {
                is Result.Success -> myMovie.data
                is Result.Error -> null
            }
        }
        return movie
    }

    fun getNowPlaying(language: String?): LiveData<List<Movie>> {
        val movie = MutableLiveData<List<Movie>>()

        uiScope.launch {
            movie.value = when (val myMovie = repository.getNowPlayingMovies(language)) {
                is Result.Success -> myMovie.data
                is Result.Error -> null
            }
        }
        return movie
    }
}