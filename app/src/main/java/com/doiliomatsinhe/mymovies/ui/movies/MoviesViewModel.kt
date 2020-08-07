package com.doiliomatsinhe.mymovies.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.doiliomatsinhe.mymovies.data.Repository
import com.doiliomatsinhe.mymovies.model.Movie
import kotlinx.coroutines.flow.Flow

class MoviesViewModel @ViewModelInject constructor(
    private val repository: Repository
) :
    ViewModel() {

    private var currentCategory: String? = null
    private var currentQuery: String? = null
    private var currentSearchresult: Flow<PagingData<Movie>>? = null
    private var currentQueryResult: Flow<PagingData<Movie>>? = null

    fun getMoviesList(category: String?, language: String?): Flow<PagingData<Movie>> {
        val lastResult = currentSearchresult
        if (category == currentCategory &&
            lastResult != null
        ) {
            return lastResult
        }

        currentCategory = category
        val newResult = repository.getMovieResultStream(category, language)
            .cachedIn(viewModelScope)
        currentSearchresult = newResult

        return newResult

    }

    fun queryMovieList(query: String): Flow<PagingData<Movie>> {
        val lastResult = currentQueryResult
        if (query == currentQuery &&
            lastResult != null
        ) {
            return lastResult
        }

        currentQuery = query
        val newResult = repository.getMovieQueryStream(query)
            .cachedIn(viewModelScope)
        currentQueryResult = newResult

        return newResult

    }


}