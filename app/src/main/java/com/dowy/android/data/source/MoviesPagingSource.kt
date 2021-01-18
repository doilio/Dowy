package com.dowy.android.data.source

import androidx.paging.PagingSource
import com.dowy.android.model.movie.Movie
import com.dowy.android.network.ApiService
import com.dowy.android.utils.CALENDAR_PATTERN
import com.dowy.android.utils.MOVIES_LIST_STARTING_PAGE
import com.dowy.android.utils.SECRET_KEY
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.io.InvalidObjectException
import java.text.SimpleDateFormat
import java.util.*

class MoviesPagingSource(
    private val service: ApiService,
    private val category: String?,
    private val language: String?
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: MOVIES_LIST_STARTING_PAGE
        return try {
            val response = if (category != null && language != null) {
                service.getMovies(category, SECRET_KEY, language, page)
            } else {
                throw InvalidObjectException("Category and Language should not be null!")
            }

            val movies = if (category == "upcoming") {
                response.results.filter {
                    it.release_date >= getCurrentDate()
                }
            } else {
                response.results
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (page == MOVIES_LIST_STARTING_PAGE) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val date = calendar.time
        val sdf = SimpleDateFormat(CALENDAR_PATTERN, Locale.getDefault())
        Timber.d("Current Date: ${sdf.format(date)}")
        return sdf.format(date)
    }
}