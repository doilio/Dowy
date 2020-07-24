package com.doiliomatsinhe.mymovies.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.doiliomatsinhe.mymovies.model.Movie
import com.doiliomatsinhe.mymovies.model.TvSeries
import com.doiliomatsinhe.mymovies.model.asDomainModel
import com.doiliomatsinhe.mymovies.network.ApiService
import com.doiliomatsinhe.mymovies.utils.SECRET_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class Repository @Inject constructor(
    private val service: ApiService,
    private val database: MoviesDao
) {


    fun getMovieResultStream(category: String?, language: String?): Flow<PagingData<Movie>> {
        return Pager(config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(service, category, language) }).flow
    }

//    suspend fun refreshMovies(category: String?, language: String?) {
//        withContext(Dispatchers.IO) {
//            try {
//                val listOfMovies = service.getMovies(category.toString(), SECRET_KEY, language.toString(), 1).results
//                database.insertAllMovies(*listOfMovies.asDatabaseModel())
//            } catch (e: Exception) {
//                Timber.d("Error reading Movies ${e.message}")
//            }
//        }
//    }
//
//    fun getMovies(): LiveData<List<Movie>> {
//        return Transformations.map(database.getMovies()) {
//            it.asDomainModel()
//        }
//    }

    suspend fun refreshSeries(category: String?, language: String?) {
        withContext(Dispatchers.IO) {
            try {
                val listOfSeries = service.getSeries(
                    category.toString(),
                    SECRET_KEY,
                    language.toString(),
                    1
                ).results
                database.insertAllSeries(*listOfSeries.asDatabaseModel())
            } catch (e: Exception) {
                Timber.d("Error reading Series ${e.message}")
            }
        }
    }

    fun getSeries(): LiveData<List<TvSeries>> {
        return Transformations.map(database.getSeries()) {
            it.asDomainModel()
        }
    }

}