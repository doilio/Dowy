package com.doiliomatsinhe.mymovies.network

import com.doiliomatsinhe.mymovies.model.movie.*
import com.doiliomatsinhe.mymovies.model.person.PersonMovieCast
import com.doiliomatsinhe.mymovies.model.person.PersonMovieCrew
import com.doiliomatsinhe.mymovies.model.person.PersonTvCast
import com.doiliomatsinhe.mymovies.model.person.PersonTvCrew
import com.doiliomatsinhe.mymovies.model.tv.*
import com.google.gson.annotations.SerializedName

data class NetworkMovie(

    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val total_results: Int,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("results") val results: List<Movie>
)

data class NetworkMovieReview(

    @SerializedName("id") val id: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieReview>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int
)

data class NetworkMovieCredit(

    @SerializedName("id") val id: Int,
    @SerializedName("cast") val cast: List<MovieCast>,
    @SerializedName("crew") val crew: List<MovieCrew>
)

data class NetworkMovieTrailer(

    @SerializedName("id") val id: Int,
    @SerializedName("results") val results: List<MovieTrailer>
)

data class NetworkMovieGenres(

    @SerializedName("genres") val genres: List<MovieGenres>
)

data class NetworkTvSeries(

    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val total_results: Int,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("results") val results: List<TvSeries>
)

data class NetworkTvReview(

    @SerializedName("id") val id: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<TvReview>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int
)

data class NetworkTvCredit(

    @SerializedName("cast") val cast: List<TvCast>,
    @SerializedName("crew") val crew: List<TvCrew>,
    @SerializedName("id") val id: Int
)

data class NetworkTvTrailer(

    @SerializedName("id") val id: Int,
    @SerializedName("results") val results: List<TvTrailer>
)

data class NetworkTvGenres(

    @SerializedName("genres") val genres: List<TvGenres>
)

data class NetworkPersonMovieCredit(

    @SerializedName("cast") val cast: List<PersonMovieCast>,
    @SerializedName("crew") val crew: List<PersonMovieCrew>,
    @SerializedName("id") val id: Int
)

data class NetworkPersonTvCredit(

    @SerializedName("cast") val cast: List<PersonTvCast>,
    @SerializedName("crew") val crew: List<PersonTvCrew>,
    @SerializedName("id") val id: Int
)