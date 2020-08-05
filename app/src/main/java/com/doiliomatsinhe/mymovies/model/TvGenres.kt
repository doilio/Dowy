package com.doiliomatsinhe.mymovies.model

import com.google.gson.annotations.SerializedName

data class TvGenres (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
)