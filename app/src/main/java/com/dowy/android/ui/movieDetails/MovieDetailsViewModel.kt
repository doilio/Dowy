package com.dowy.android.ui.movieDetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dowy.android.data.Repository
import com.dowy.android.model.movie.MovieCast
import com.dowy.android.model.movie.MovieGenres
import com.dowy.android.model.movie.MovieReview
import com.dowy.android.model.movie.MovieTrailer
import com.dowy.android.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieDetailsViewModel @ViewModelInject
constructor(private val repository: Repository) :
    ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // In-Memory Caching
    private var currentMovieCastResult: MutableLiveData<List<MovieCast>>? = null
    private var currentMovieTrailersResult: MutableLiveData<List<MovieTrailer>>? = null
    private var currentMovieReviewResult: MutableLiveData<List<MovieReview>>? = null
    private var currentMovieId: Int? = null

    private val _listOfGenres = MutableLiveData<List<MovieGenres>>()
    val listOfGenres: LiveData<List<MovieGenres>>
        get() = _listOfGenres


    init {
        uiScope.launch {
            _listOfGenres.value = when (val movieGenres = repository.getMovieGenres()) {
                is Result.Success -> movieGenres.data
                is Result.Error -> null
            }
        }
    }

    fun getMovieReview(movieId: Int): LiveData<List<MovieReview>> {
        val reviewList = MutableLiveData<List<MovieReview>>()

        val lastResult = currentMovieReviewResult
        if (movieId == currentMovieId && lastResult != null) {
            return lastResult
        }

        currentMovieId = movieId
        uiScope.launch {
            reviewList.value = when (val movieReviews = repository.getMovieReviews(movieId)) {
                is Result.Success -> {
                    if (movieReviews.data.size > 3) {
                        movieReviews.data.subList(0, 3)
                    } else {
                        movieReviews.data
                    }
                }
                is Result.Error -> null
            }
            currentMovieReviewResult = reviewList
        }
        return reviewList
    }

    fun getMovieTrailers(movieId: Int): LiveData<List<MovieTrailer>> {
        val trailers = MutableLiveData<List<MovieTrailer>>()

        val lastResult = currentMovieTrailersResult
        if (movieId == currentMovieId && lastResult != null) {
            return lastResult
        }

        currentMovieId = movieId
        uiScope.launch {
            trailers.value = when (val result = repository.getMovieTrailer(movieId)) {
                is Result.Success -> result.data
                is Result.Error -> null
            }
            currentMovieTrailersResult = trailers
        }
        return trailers
    }

    fun getMovieCast(movieId: Int): LiveData<List<MovieCast>> {
        val castMembers = MutableLiveData<List<MovieCast>>()

        val lastResult = currentMovieCastResult
        if (movieId == currentMovieId && lastResult != null) {
            return lastResult
        }

        currentMovieId = movieId
        uiScope.launch {
            castMembers.value = when (val result = repository.getMovieCast(movieId)) {
                is Result.Success -> result.data.filter { it.profile_path != null }
                is Result.Error -> null
            }
            currentMovieCastResult = castMembers
        }
        return castMembers
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}