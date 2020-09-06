package com.doiliomatsinhe.mymovies.model.person

import com.google.gson.annotations.SerializedName

data class PersonMovieCredit(

    @SerializedName("cast") val cast: List<PersonMovieCast>,
    @SerializedName("crew") val crew: List<PersonMovieCrew>,
    @SerializedName("id") val id: Int
)