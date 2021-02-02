package com.dowy.android.network

import com.dowy.android.model.person.Person
import com.dowy.android.utils.*
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
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String
    ): NetworkMovieGenres

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReview(
        @Path(MOVIE_ID) movieId: Int,
        @Query(API_KEY) apiKey: String
    ): NetworkMovieReview

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path(MOVIE_ID) movieId: Int,
        @Query(API_KEY) apiKey: String
    ): NetworkMovieCredit

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailers(
        @Path(MOVIE_ID) movieId: Int,
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String
    ): NetworkMovieTrailer

    @GET("tv/{category}")
    suspend fun getSeries(
        @Path(CATEGORY) category: String,
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int
    ): NetworkTvSeries

    @GET("genre/tv/list")
    suspend fun getTvGenres(
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String
    ): NetworkTvGenres

    @GET("tv/{tv_id}/reviews")
    suspend fun getTvReview(
        @Path(TV_ID) tvId: Int,
        @Query(API_KEY) apiKey: String
    ): NetworkTvReview

    @GET("tv/{tv_id}/credits")
    suspend fun getTvCredits(
        @Path(TV_ID) tvId: Int,
        @Query(API_KEY) apiKey: String
    ): NetworkTvCredit

    @GET("tv/{tv_id}/videos")
    suspend fun getTvTrailers(
        @Path(TV_ID) tvId: Int,
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String
    ): NetworkTvTrailer

    @GET("search/movie")
    suspend fun queryMovie(
        @Query(QUERY) query: String,
        @Query(API_KEY) apiKey: String,
        @Query(PAGE) page: Int
    ): NetworkMovie

    @GET("search/tv")
    suspend fun querySeries(
        @Query(QUERY) query: String,
        @Query(API_KEY) apiKey: String,
        @Query(PAGE) page: Int
    ): NetworkTvSeries

    @GET("person/{person_id}")
    suspend fun getPerson(
        @Path(PERSON_ID) personId: Int,
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String
    ): Person

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonMovieCredit(
        @Path(PERSON_ID) personId: Int,
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String
    ): NetworkPersonMovieCredit

    @GET("person/{person_id}/tv_credits")
    suspend fun getPersonSeriesCredit(
        @Path(PERSON_ID) personId: Int,
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String
    ): NetworkPersonTvCredit
}


