package com.doiliomatsinhe.mymovies.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doiliomatsinhe.mymovies.data.Repository
import com.doiliomatsinhe.mymovies.model.MovieCast
import com.doiliomatsinhe.mymovies.model.MovieGenres
import com.doiliomatsinhe.mymovies.model.MovieReview
import com.doiliomatsinhe.mymovies.model.MovieTrailer
import com.doiliomatsinhe.mymovies.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModel @ViewModelInject
constructor(private val repository: Repository) :
    ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _listOfGenres = MutableLiveData<List<MovieGenres>>()
    val listOfGenres: LiveData<List<MovieGenres>>
        get() = _listOfGenres


    init {
        uiScope.launch {
            _listOfGenres.value = when (val movieGenres = repository.getGenreList()) {
                is Result.Success -> movieGenres.data
                is Result.Error -> null
            }
        }
    }

    fun getMovieReview(movieId: Int): LiveData<List<MovieReview>> {
        val reviewList = MutableLiveData<List<MovieReview>>()

        uiScope.launch {
            reviewList.value = when (val movieReviews = repository.getMovieReviews(movieId)) {
                is Result.Success -> movieReviews.data
                is Result.Error -> null
            }
        }
        return reviewList
    }

    fun getMovieTrailers(movieId: Int): LiveData<List<MovieTrailer>> {
        val trailers = MutableLiveData<List<MovieTrailer>>()

        uiScope.launch {
            trailers.value = when (val result = repository.getMovieTrailer(movieId)) {
                is Result.Success -> result.data
                is Result.Error -> null
            }
        }
        return trailers
    }

    fun getMovieCast(movieId: Int): LiveData<List<MovieCast>> {
        val castMembers = MutableLiveData<List<MovieCast>>()

        uiScope.launch {
            castMembers.value = when (val result = repository.getCast(movieId)) {
                is Result.Success -> result.data.filter { it.profile_path != null }
                is Result.Error -> null
            }
        }
        return castMembers
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}