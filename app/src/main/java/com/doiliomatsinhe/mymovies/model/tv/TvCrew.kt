package com.doiliomatsinhe.mymovies.model.tv

import com.google.gson.annotations.SerializedName

data class TvCrew (

    @SerializedName("credit_id") val credit_id : String,
    @SerializedName("department") val department : String,
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("gender") val gender : Int,
    @SerializedName("job") val job : String,
    @SerializedName("profile_path") val profile_path : String
)