package com.doiliomatsinhe.mymovies.network

import com.doiliomatsinhe.mymovies.model.Movie
import com.doiliomatsinhe.mymovies.model.Review
import com.doiliomatsinhe.mymovies.model.Trailer
import com.google.gson.annotations.SerializedName

data class NetworkReview(

    @SerializedName("id") val id: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Review>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int
)

data class NetworkMovie(

    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val total_results: Int,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("results") val results: List<Movie>
)

data class NetworkTrailer(

    @SerializedName("id") val id: Int,
    @SerializedName("results") val results: List<Trailer>
)