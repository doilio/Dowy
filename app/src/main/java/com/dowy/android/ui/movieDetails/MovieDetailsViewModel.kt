package com.dowy.android.ui.movieDetails

import android.content.SharedPreferences
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dowy.android.data.Repository
import com.dowy.android.model.movie.*
import com.dowy.android.utils.DEFAULT_LANGUAGE
import com.dowy.android.utils.LANGUAGE_KEY
import com.dowy.android.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieDetailsViewModel @ViewModelInject
constructor(
    private val repository: Repository,
    preference: SharedPreferences,
    @Assisted savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val language = preference.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE)!!
    private val movieId = savedStateHandle.get<Movie>("Movie")?.id!!

    // In-Memory Caching
    private var currentMovieCastResult: MutableLiveData<List<MovieCast>>? = null
    private var currentMovieTrailersResult: MutableLiveData<List<MovieTrailer>>? = null
    private var currentMovieReviewResult: MutableLiveData<List<MovieReview>>? = null
    private var currentMovieGenreResult: MutableLiveData<List<MovieGenres>>? = null
    private var currentMovieId: Int? = null
    private var currentLanguage: String? = null

    fun getMovieGenre(): LiveData<List<MovieGenres>> {
        val genreList = MutableLiveData<List<MovieGenres>>()

        val lastResult = currentMovieGenreResult
        if (language == currentLanguage && lastResult != null) {
            return lastResult
        }

        currentLanguage = language
        uiScope.launch {
            genreList.value = when (val movieGenres = repository.getMovieGenres(language)) {
                is Result.Success -> movieGenres.data
                is Result.Error -> null
            }
            currentMovieGenreResult = genreList
        }
        return genreList
    }

    fun getMovieReview(): LiveData<List<MovieReview>> {
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

    fun getMovieTrailers(): LiveData<List<MovieTrailer>> {
        val trailers = MutableLiveData<List<MovieTrailer>>()

        val lastResult = currentMovieTrailersResult
        if (movieId == currentMovieId && language == currentLanguage && lastResult != null) {
            return lastResult
        }

        currentMovieId = movieId
        uiScope.launch {
            trailers.value = when (val result = repository.getMovieTrailer(movieId, language)) {
                is Result.Success -> result.data
                is Result.Error -> null
            }
            currentMovieTrailersResult = trailers
        }
        return trailers
    }

    fun getMovieCast(): LiveData<List<MovieCast>> {
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