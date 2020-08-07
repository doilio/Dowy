package com.doiliomatsinhe.mymovies.data.source

import androidx.paging.PagingSource
import com.doiliomatsinhe.mymovies.model.Movie
import com.doiliomatsinhe.mymovies.network.ApiService
import com.doiliomatsinhe.mymovies.utils.MOVIES_LIST_STARTING_PAGE
import com.doiliomatsinhe.mymovies.utils.SECRET_KEY
import retrofit2.HttpException
import java.io.IOException

class MovieQueryPagingSource(
    private val query: String,
    private val service: ApiService
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: MOVIES_LIST_STARTING_PAGE
        return try {
            val response = service.queryMovie(query, SECRET_KEY, page)

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