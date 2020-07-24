package com.doiliomatsinhe.mymovies.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.doiliomatsinhe.mymovies.data.Repository
import com.doiliomatsinhe.mymovies.model.Movie
import kotlinx.coroutines.flow.Flow

class MoviesViewModel(
    private val repository: Repository,
    private val category: String?,
    private val language: String?
) :
    ViewModel() {

    private var currentCategory: String? = null
    private var currentSearchresult: Flow<PagingData<Movie>>? = null

    fun getMoviesList(): Flow<PagingData<Movie>> {
        val lastResult = currentSearchresult
        if (category == currentCategory &&
            lastResult != null) {
            return lastResult
        }

        currentCategory = category
        val newResult = repository.getMovieResultStream(category, language)
            .cachedIn(viewModelScope)
        currentSearchresult = newResult

        return newResult

    }
/*    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val listOfMovies = MediatorLiveData<List<Movie>>()

    init {
        uiScope.launch {
            repository.refreshMovies(category, language)
        }
        listOfMovies.addSource(repository.getMovies(), listOfMovies::setValue)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }*/
}