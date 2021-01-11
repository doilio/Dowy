package com.dowy.android.model.person

import com.google.gson.annotations.SerializedName

data class PersonTvCast(

    @SerializedName("credit_id") val credit_id: String,
    @SerializedName("original_name") val original_name: String,
    @SerializedName("id") val id: Int,
    @SerializedName("genre_ids") val genre_ids: List<Int>,
    @SerializedName("character") val character: String,
    @SerializedName("name") val name: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("vote_count") val vote_count: Int,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("episode_count") val episode_count: Int,
    @SerializedName("original_language") val original_language: String,
    @SerializedName("first_air_date") val first_air_date: String,
    @SerializedName("backdrop_path") val backdrop_path: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("origin_country") val origin_country: List<String>
) {
    val fullProfilePath: String
        get() = "http://image.tmdb.org/t/p/w342$poster_path"
}