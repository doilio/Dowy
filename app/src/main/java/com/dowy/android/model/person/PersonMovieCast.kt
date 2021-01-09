package com.dowy.android.model.person

import com.google.gson.annotations.SerializedName

data class PersonMovieCast(

    @SerializedName("character") val character: String,
    @SerializedName("credit_id") val credit_id: String,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("vote_count") val vote_count: Int,
    @SerializedName("video") val video: Boolean,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("title") val title: String,
    @SerializedName("genre_ids") val genre_ids: List<Int>,
    @SerializedName("original_language") val original_language: String,
    @SerializedName("original_title") val original_title: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("id") val id: Int,
    @SerializedName("backdrop_path") val backdrop_path: String? ="",
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val poster_path: String
) {
    val fullProfilePath: String
        get() = "http://image.tmdb.org/t/p/w342$poster_path"
}