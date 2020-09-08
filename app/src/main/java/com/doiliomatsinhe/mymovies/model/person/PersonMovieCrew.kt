package com.doiliomatsinhe.mymovies.model.person

import com.google.gson.annotations.SerializedName

data class PersonMovieCrew(

    @SerializedName("id") val id: Int,
    @SerializedName("department") val department: String,
    @SerializedName("original_language") val original_language: String,
    @SerializedName("original_title") val original_title: String,
    @SerializedName("job") val job: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("vote_count") val vote_count: Int,
    @SerializedName("video") val video: Boolean,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("backdrop_path") val backdrop_path: String,
    @SerializedName("title") val title: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("genre_ids") val genre_ids: List<Int>,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("credit_id") val credit_id: String
)