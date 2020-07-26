package com.doiliomatsinhe.mymovies.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.doiliomatsinhe.mymovies.data.source.MoviesPagingSource
import com.doiliomatsinhe.mymovies.data.source.TvSeriesPagingSource
import com.doiliomatsinhe.mymovies.model.Movie
import com.doiliomatsinhe.mymovies.model.TvSeries
import com.doiliomatsinhe.mymovies.network.ApiService
import kotlinx.coroutines.flow.Flow
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

}