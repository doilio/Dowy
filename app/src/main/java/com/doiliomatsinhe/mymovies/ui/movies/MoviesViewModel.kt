package com.doiliomatsinhe.mymovies.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doiliomatsinhe.mymovies.data.Repository
import com.doiliomatsinhe.mymovies.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MoviesViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _listOfMovies = MutableLiveData<List<Movie>>()
    val listOfMovies: LiveData<List<Movie>>
        get() = _listOfMovies

    init {
        uiScope.launch {
            _listOfMovies.value = repository.getMovies()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}