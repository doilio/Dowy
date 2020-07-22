package com.doiliomatsinhe.mymovies.data

import com.doiliomatsinhe.mymovies.model.Movie
import com.doiliomatsinhe.mymovies.model.TvSeries
import com.doiliomatsinhe.mymovies.network.ApiService
import com.doiliomatsinhe.mymovies.utils.SECRET_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class Repository @Inject constructor(private val service: ApiService) {


    suspend fun getMovies(): List<Movie> {
        return withContext(Dispatchers.IO) {
            val listOfMovies = service.getMovies("popular", SECRET_KEY, "en-US", 1).results

            Timber.d("Repository list $listOfMovies")
            listOfMovies

        }

    }

    suspend fun getTvSeries(): List<TvSeries> {
        return withContext(Dispatchers.IO) {
            val listOfSeries = service.getSeries("popular", SECRET_KEY, "en-US", 1).results

            Timber.d("Repository list of series$listOfSeries")
            listOfSeries
        }

    }
}