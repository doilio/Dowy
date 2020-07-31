package com.doiliomatsinhe.mymovies.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doiliomatsinhe.mymovies.data.Repository
import com.doiliomatsinhe.mymovies.model.MovieCast
import com.doiliomatsinhe.mymovies.model.MovieGenres
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsViewModel @ViewModelInject
constructor(private val repository: Repository) :
    ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _listOfGenres = MutableLiveData<List<MovieGenres>>()
    val listOfGenres: LiveData<List<MovieGenres>>
        get() = _listOfGenres

    private val _castMembers = MutableLiveData<List<MovieCast>>()


    init {
        uiScope.launch {
            _listOfGenres.value = repository.getGenreList()
        }

    }

    fun getMovieCast(movieId: Int): LiveData<List<MovieCast>> {
        uiScope.launch {
            _castMembers.value = repository.getCast(movieId)
        }
        return _castMembers
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}