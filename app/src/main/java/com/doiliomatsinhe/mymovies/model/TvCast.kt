package com.doiliomatsinhe.mymovies.model

import com.google.gson.annotations.SerializedName

data class TvCast(

    @SerializedName("character") val character: String,
    @SerializedName("credit_id") val credit_id: String,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("profile_path") val profile_path: String?,
    @SerializedName("order") val order: Int
) {
    val fullProfilePath: String
        get() = "http://image.tmdb.org/t/p/w342$profile_path"
}