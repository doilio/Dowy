package com.doiliomatsinhe.mymovies.model.movie

import com.google.gson.annotations.SerializedName

data class MovieGenres (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
)