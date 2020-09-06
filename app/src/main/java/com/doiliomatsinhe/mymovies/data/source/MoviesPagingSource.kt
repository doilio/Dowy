package com.doiliomatsinhe.mymovies.data.source

import androidx.paging.PagingSource
import com.doiliomatsinhe.mymovies.model.movie.Movie
import com.doiliomatsinhe.mymovies.network.ApiService
import com.doiliomatsinhe.mymovies.utils.MOVIES_LIST_STARTING_PAGE
import com.doiliomatsinhe.mymovies.utils.SECRET_KEY
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

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
            val movies = response.results
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
}