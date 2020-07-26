package com.doiliomatsinhe.mymovies.ui.series


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.doiliomatsinhe.mymovies.data.Repository
import com.doiliomatsinhe.mymovies.model.TvSeries
import kotlinx.coroutines.flow.Flow

class TvSeriesViewModel(
    private val repository: Repository,
    private val category: String?,
    private val language: String?
) :
    ViewModel() {

    private var currentCategory: String? = null
    private var currentSearchResult: Flow<PagingData<TvSeries>>? = null


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

}