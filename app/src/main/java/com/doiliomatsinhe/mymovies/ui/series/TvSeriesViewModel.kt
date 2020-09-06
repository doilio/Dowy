package com.doiliomatsinhe.mymovies.ui.series


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.doiliomatsinhe.mymovies.data.Repository
import com.doiliomatsinhe.mymovies.model.tv.TvSeries
import kotlinx.coroutines.flow.Flow

class TvSeriesViewModel @ViewModelInject constructor(
    private val repository: Repository
) :
    ViewModel() {

    private var currentCategory: String? = null
    private var currentQuery: String? = null
    private var currentSearchResult: Flow<PagingData<TvSeries>>? = null
    private var currentQueryResult: Flow<PagingData<TvSeries>>? = null


    fun getTvSeriesList(category: String?, language: String?): Flow<PagingData<TvSeries>> {
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