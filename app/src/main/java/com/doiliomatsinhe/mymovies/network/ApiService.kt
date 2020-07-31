package com.doiliomatsinhe.mymovies.network

import com.doiliomatsinhe.mymovies.utils.*
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

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query(API_KEY) apiKey: String
    ): NetworkMovieGenres

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path(MOVIE_ID) movieId: Int,
        @Query(API_KEY) apiKey: String
    ): NetworkMovieCredit

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailers(
        @Path(MOVIE_ID) movieId: Int,
        @Query(API_KEY) apiKey: String
    ): NetworkMovieTrailer

    @GET("tv/{category}")
    suspend fun getSeries(
        @Path(CATEGORY) category: String,
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int
    ): NetworkTvSeries

}

