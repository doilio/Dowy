package com.doiliomatsinhe.mymovies.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.doiliomatsinhe.mymovies.data.source.MoviesPagingSource
import com.doiliomatsinhe.mymovies.data.source.TvSeriesPagingSource
import com.doiliomatsinhe.mymovies.model.*
import com.doiliomatsinhe.mymovies.network.ApiService
import com.doiliomatsinhe.mymovies.utils.Result
import com.doiliomatsinhe.mymovies.utils.SECRET_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
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

    suspend fun getMovieReviews(movieId: Int): Result<List<MovieReview>> {
        return withContext(Dispatchers.IO) {
            try {
                val movieReviews = service.getMovieReview(movieId, SECRET_KEY).results
                Result.Success(movieReviews)

            } catch (exception: IOException) {
                Result.Error(exception)
            } catch (exception: HttpException) {
                Result.Error(exception)
            }
        }
    }

    suspend fun getMovieTrailer(movieId: Int): Result<List<MovieTrailer>> {
        return withContext(Dispatchers.IO) {
            try {
                val movieTrailers = service.getMovieTrailers(movieId, SECRET_KEY).results
                Result.Success(movieTrailers)

            } catch (exception: IOException) {
                Result.Error(exception)
            } catch (exception: HttpException) {
                Result.Error(exception)
            }
        }
    }

    suspend fun getCast(movieId: Int): Result<List<MovieCast>> {
        return withContext(Dispatchers.IO) {
            try {
                val castMembers = service.getMovieCredits(movieId, SECRET_KEY).cast
                Result.Success(castMembers)

            } catch (exception: IOException) {
                Result.Error(exception)
            } catch (exception: HttpException) {
                Result.Error(exception)
            }
        }
    }

    suspend fun getGenreList(): Result<List<MovieGenres>> {
        return withContext(Dispatchers.IO) {
            try {
                val movieGenres = service.getMovieGenres(SECRET_KEY).genres
                Result.Success(movieGenres)

            } catch (exception: IOException) {
                Result.Error(exception)
            } catch (exception: HttpException) {
                Result.Error(exception)
            }
        }
    }

}