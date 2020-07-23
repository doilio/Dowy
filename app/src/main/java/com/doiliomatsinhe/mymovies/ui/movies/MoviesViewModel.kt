package com.doiliomatsinhe.mymovies.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
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

    val listOfMovies = MediatorLiveData<List<Movie>>()

    init {
        uiScope.launch {
            repository.refreshMovies()
        }
        listOfMovies.addSource(repository.getMovies(), listOfMovies::setValue)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}