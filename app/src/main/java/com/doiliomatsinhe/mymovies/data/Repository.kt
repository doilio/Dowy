package com.doiliomatsinhe.mymovies.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.doiliomatsinhe.mymovies.data.source.MoviesPagingSource
import com.doiliomatsinhe.mymovies.data.source.TvSeriesPagingSource
import com.doiliomatsinhe.mymovies.model.Movie
import com.doiliomatsinhe.mymovies.model.MovieCast
import com.doiliomatsinhe.mymovies.model.MovieGenres
import com.doiliomatsinhe.mymovies.model.TvSeries
import com.doiliomatsinhe.mymovies.network.ApiService
import com.doiliomatsinhe.mymovies.utils.SECRET_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val service: ApiService
) {

    fun getMovieResultStream(category: String?, language: String?):
            Flow<PagingData<Movie>> {
        return Pager(config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    service,
                    category,
                    language
                )
            }).flow
    }

    fun getSeriesResultStream(category: String?, language: String?):
            Flow<PagingData<TvSeries>> {
        return Pager(config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
            pagingSourceFactory = {
                TvSeriesPagingSource(
                    service,
                    category,
                    language
                )
            }).flow

    }

    suspend fun getCast(movieId: Int): List<MovieCast> {
        return withContext(Dispatchers.IO) {
            service.getMovieCredits(movieId, SECRET_KEY).cast
        }
    }

    suspend fun getGenreList(): List<MovieGenres> {
        return withContext(Dispatchers.IO) {
            service.getMovieGenres(SECRET_KEY).genres
        }
    }

}