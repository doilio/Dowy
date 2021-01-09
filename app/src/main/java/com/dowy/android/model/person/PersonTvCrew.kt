package com.dowy.android.model.person

import com.google.gson.annotations.SerializedName

data class PersonTvCrew (

    @SerializedName("id") val id : Int,
    @SerializedName("department") val department : String,
    @SerializedName("original_language") val original_language : String,
    @SerializedName("episode_count") val episode_count : Int,
    @SerializedName("job") val job : String,
    @SerializedName("overview") val overview : String,
    @SerializedName("origin_country") val origin_country : List<String>,
    @SerializedName("original_name") val original_name : String,
    @SerializedName("genre_ids") val genre_ids : List<Int>,
    @SerializedName("name") val name : String,
    @SerializedName("first_air_date") val first_air_date : String,
    @SerializedName("backdrop_path") val backdrop_path : String,
    @SerializedName("popularity") val popularity : Double,
    @SerializedName("vote_count") val vote_count : Int,
    @SerializedName("vote_average") val vote_average : Float,
    @SerializedName("poster_path") val poster_path : String,
    @SerializedName("credit_id") val credit_id : String
)