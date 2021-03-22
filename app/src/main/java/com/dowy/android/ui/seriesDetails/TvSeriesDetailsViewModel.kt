package com.dowy.android.ui.seriesDetails

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dowy.android.data.Repository
import com.dowy.android.model.tv.*
import com.dowy.android.utils.DEFAULT_LANGUAGE
import com.dowy.android.utils.LANGUAGE_KEY
import com.dowy.android.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvSeriesDetailsViewModel @Inject
constructor(
    private val repository: Repository,
    preference: SharedPreferences,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val language = preference.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE)!!
    private val tvId = savedStateHandle.get<TvSeries>("TvSeries")?.id!!

    // In-Memory Caching
    private var currentTvCastResult: MutableLiveData<List<TvCast>>? = null
    private var currentTvTrailersResult: MutableLiveData<List<TvTrailer>>? = null
    private var currentTvReviewResult: MutableLiveData<List<TvReview>>? = null
    private var currentMovieGenreResult: MutableLiveData<List<TvGenres>>? = null
    private var currentTvId: Int? = null
    private var currentLanguage: String? = null

    fun getTvGenre(): LiveData<List<TvGenres>> {
        val genreList = MutableLiveData<List<TvGenres>>()

        val lastResult = currentMovieGenreResult
        if (language == currentLanguage && lastResult != null) {
            return lastResult
        }

        currentLanguage = language
        uiScope.launch {
            genreList.value = when (val tvGenres = repository.getTvGenres(language)) {
                is Result.Success -> tvGenres.data
                is Result.Error -> null
            }
            currentMovieGenreResult = genreList
        }
        return genreList
    }

    fun getTvReview(): LiveData<List<TvReview>> {
        val reviewList = MutableLiveData<List<TvReview>>()

        val lastResult = currentTvReviewResult
        if (tvId == currentTvId && lastResult != null) {
            return lastResult
        }

        currentTvId = tvId
        uiScope.launch {
            reviewList.value = when (val tvReviews = repository.getTvReviews(tvId)) {
                is Result.Success -> {
                    if (tvReviews.data.size > 3) {
                        tvReviews.data.subList(0, 3)
                    } else {
                        tvReviews.data
                    }
                }
                is Result.Error -> null
            }
            currentTvReviewResult = reviewList
        }
        return reviewList
    }

    fun getTvTrailers(): LiveData<List<TvTrailer>> {
        val trailers = MutableLiveData<List<TvTrailer>>()

        val lastResult = currentTvTrailersResult
        if (tvId == currentTvId && language == currentLanguage && lastResult != null) {
            return lastResult
        }

        currentTvId = tvId
        uiScope.launch {
            trailers.value = when (val result = repository.getTvTrailer(tvId, language)) {
                is Result.Success -> result.data
                is Result.Error -> null
            }
            currentTvTrailersResult = trailers
        }
        return trailers
    }

    fun getTvCast(): LiveData<List<TvCast>> {
        val castMembers = MutableLiveData<List<TvCast>>()

        val lastResult = currentTvCastResult
        if (tvId == currentTvId && lastResult != null) {
            return lastResult
        }

        currentTvId = tvId
        uiScope.launch {
            castMembers.value = when (val result = repository.getTvCast(tvId)) {
                is Result.Success -> result.data.filter { it.profile_path != null }
                is Result.Error -> null
            }
            currentTvCastResult = castMembers
        }
        return castMembers
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}