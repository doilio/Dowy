package com.doiliomatsinhe.mymovies.network

import com.doiliomatsinhe.mymovies.utils.API_KEY
import com.doiliomatsinhe.mymovies.utils.CATEGORY
import com.doiliomatsinhe.mymovies.utils.LANGUAGE
import com.doiliomatsinhe.mymovies.utils.PAGE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/{category}")
    suspend fun getMovies(
        @Path(CATEGORY) category: String,
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int
    ): NetworkMovie

    @GET("tv/{category}")
    suspend fun getSeries(
        @Path(CATEGORY) category: String,
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int
    ): NetworkTvSeries

}

