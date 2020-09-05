package com.doiliomatsinhe.mymovies.model

import com.google.gson.annotations.SerializedName

data class PersonTvCredit(

    @SerializedName("cast") val cast: List<PersonTvCast>,
    @SerializedName("crew") val crew: List<String>,
    @SerializedName("id") val id: Int
)