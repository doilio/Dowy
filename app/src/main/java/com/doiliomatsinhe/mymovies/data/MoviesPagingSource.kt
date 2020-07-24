package com.doiliomatsinhe.mymovies.data

import androidx.paging.PagingSource
import com.doiliomatsinhe.mymovies.model.Movie
import com.doiliomatsinhe.mymovies.network.ApiService
import com.doiliomatsinhe.mymovies.utils.SECRET_KEY
import retrofit2.HttpException
import java.io.IOException

const val MOVIES_LIST_STARTING_PAGE = 1

class MoviesPagingSource(
    private val service: ApiService,
    private val category: String?,
    private val language: String?
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: MOVIES_LIST_STARTING_PAGE
        return try {
            val response =
                service.getMovies(category.toString(), SECRET_KEY, language.toString(), position)
            val movies = response.results
            LoadResult.Page(
                data = movies,
                prevKey = if (position == MOVIES_LIST_STARTING_PAGE) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}