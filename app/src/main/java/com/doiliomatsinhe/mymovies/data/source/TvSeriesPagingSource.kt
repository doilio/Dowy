package com.doiliomatsinhe.mymovies.data.source

import androidx.paging.PagingSource
import com.doiliomatsinhe.mymovies.model.tv.TvSeries
import com.doiliomatsinhe.mymovies.network.ApiService
import com.doiliomatsinhe.mymovies.utils.SECRET_KEY
import com.doiliomatsinhe.mymovies.utils.SERIES_LIST_STARTING_PAGE
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

class TvSeriesPagingSource(
    private val service: ApiService,
    private val category: String?,
    private val language: String?
) : PagingSource<Int, TvSeries>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvSeries> {
        val page = params.key ?: SERIES_LIST_STARTING_PAGE
        return try {
            val response = if (category != null && language != null) {
                service.getSeries(category, SECRET_KEY, language, page)
            } else {
                throw InvalidObjectException("Category and Language should not be null!")
            }
            val series = response.results
            LoadResult.Page(
                data = series,
                prevKey = if (page == SERIES_LIST_STARTING_PAGE) null else page - 1,
                nextKey = if (series.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }


}