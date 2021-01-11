package com.dowy.android.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dowy.android.data.source.MovieQueryPagingSource
import com.dowy.android.data.source.MoviesPagingSource
import com.dowy.android.data.source.TvSeriesPagingSource
import com.dowy.android.data.source.TvSeriesQueryPagingSource
import com.dowy.android.model.movie.*
import com.dowy.android.model.person.Person
import com.dowy.android.model.person.PersonMovieCast
import com.dowy.android.model.person.PersonTvCast
import com.dowy.android.model.tv.*
import com.dowy.android.network.ApiService
import com.dowy.android.utils.Result
import com.dowy.android.utils.SECRET_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
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

    fun getMovieQueryStream(query: String):
            Flow<PagingData<Movie>> {
        return Pager(config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
            pagingSourceFactory = {
                MovieQueryPagingSource(
                    query,
                    service
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

    fun getSeriesQueryStream(query: String):
            Flow<PagingData<TvSeries>> {
        return Pager(config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
            pagingSourceFactory = {
                TvSeriesQueryPagingSource(
                    query,
                    service
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

    suspend fun getMovieCast(movieId: Int): Result<List<MovieCast>> {
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

    suspend fun getMovieGenres(): Result<List<MovieGenres>> {
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

    suspend fun getTvReviews(tvId: Int): Result<List<TvReview>> {
        return withContext(Dispatchers.IO) {
            try {
                val tvReviews = service.getTvReview(tvId, SECRET_KEY).results
                Result.Success(tvReviews)

            } catch (exception: IOException) {
                Result.Error(exception)
            } catch (exception: HttpException) {
                Result.Error(exception)
            }
        }
    }

    suspend fun getTvTrailer(tvId: Int): Result<List<TvTrailer>> {
        return withContext(Dispatchers.IO) {
            try {
                val tvTrailers = service.getTvTrailers(tvId, SECRET_KEY).results
                Result.Success(tvTrailers)

            } catch (exception: IOException) {
                Result.Error(exception)
            } catch (exception: HttpException) {
                Result.Error(exception)
            }
        }
    }

    suspend fun getTvCast(tvId: Int): Result<List<TvCast>> {
        return withContext(Dispatchers.IO) {
            try {
                val castMembers = service.getTvCredits(tvId, SECRET_KEY).cast
                Result.Success(castMembers)

            } catch (exception: IOException) {
                Result.Error(exception)
            } catch (exception: HttpException) {
                Result.Error(exception)
            }
        }
    }

    suspend fun getTvGenres(): Result<List<TvGenres>> {
        return withContext(Dispatchers.IO) {
            try {
                val tvGenres = service.getTvGenres(SECRET_KEY).genres
                Result.Success(tvGenres)

            } catch (exception: IOException) {
                Result.Error(exception)
            } catch (exception: HttpException) {
                Result.Error(exception)
            }
        }
    }

    suspend fun getPerson(personId: Int): Person {
        return withContext(Dispatchers.IO) {
            service.getPerson(personId, SECRET_KEY)
        }
    }

    suspend fun getPersonMovies(personId: Int): Result<List<PersonMovieCast>> {
        return withContext(Dispatchers.IO) {
            try {
                val personCast = service.getPersonMovieCredit(personId, SECRET_KEY).cast
                Result.Success(personCast)

            } catch (exception: IOException) {
                Result.Error(exception)
            } catch (exception: HttpException) {
                Result.Error(exception)
            }

        }
    }

    suspend fun getPersonSeries(personId: Int): Result<List<PersonTvCast>> {
        return withContext(Dispatchers.IO) {
            try {
                val personCast = service.getPersonSeriesCredit(personId, SECRET_KEY).cast
                Result.Success(personCast)

            } catch (exception: IOException) {
                Result.Error(exception)
            } catch (exception: HttpException) {
                Result.Error(exception)
            }

        }
    }

}