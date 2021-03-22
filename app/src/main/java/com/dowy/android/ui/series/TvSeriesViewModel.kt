package com.dowy.android.ui.series


import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dowy.android.data.Repository
import com.dowy.android.model.tv.TvSeries
import com.dowy.android.utils.DEFAULT_CATEGORY
import com.dowy.android.utils.DEFAULT_LANGUAGE
import com.dowy.android.utils.LANGUAGE_KEY
import com.dowy.android.utils.TV_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TvSeriesViewModel @Inject constructor(
    private val repository: Repository,
    preference: SharedPreferences
) :
    ViewModel() {

    private val language = preference.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE)
    private val category = preference.getString(TV_KEY, DEFAULT_CATEGORY)
    private var currentCategory: String? = null
    private var currentQuery: String? = null
    private var currentSearchResult: Flow<PagingData<TvSeries>>? = null
    private var currentQueryResult: Flow<PagingData<TvSeries>>? = null


    fun getTvSeriesList(): Flow<PagingData<TvSeries>> {
        val lastResult = currentSearchResult
        if (currentCategory == category && lastResult != null) {
            return lastResult
        }

        currentCategory = category
        val newResult =
            repository.getSeriesResultStream(category, language)
                .cachedIn(viewModelScope)
        currentSearchResult = newResult

        return newResult
    }

    fun querySeriesList(query: String): Flow<PagingData<TvSeries>> {
        val lastResult = currentQueryResult
        if (currentQuery == query && lastResult != null) {
            return lastResult
        }

        currentQuery = query
        val newResult =
            repository.getSeriesQueryStream(query)
                .cachedIn(viewModelScope)
        currentQueryResult = newResult

        return newResult
    }

}