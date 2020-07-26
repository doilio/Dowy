package com.doiliomatsinhe.mymovies.model

import com.doiliomatsinhe.mymovies.data.db.DatabaseSeries
import com.google.gson.annotations.SerializedName

data class TvSeries (

    @SerializedName("original_name") val original_name : String,
    @SerializedName("genre_ids") val genre_ids : List<Int>,
    @SerializedName("name") val name : String,
    @SerializedName("popularity") val popularity : Double,
    @SerializedName("origin_country") val origin_country : List<String>,
    @SerializedName("vote_count") val vote_count : Int,
    @SerializedName("first_air_date") val first_air_date : String,
    @SerializedName("backdrop_path") val backdrop_path : String?="",
    @SerializedName("original_language") val original_language : String,
    @SerializedName("id") val id : Int,
    @SerializedName("vote_average") val vote_average : Double,
    @SerializedName("overview") val overview : String,
    @SerializedName("poster_path") val poster_path : String
){
    val fullPosterPath: String
        get() = "http://image.tmdb.org/t/p/w342$poster_path"
}

fun List<DatabaseSeries>.asDomainModel(): List<TvSeries> {

    return map {
        TvSeries(
            id = it.id,
            original_name = it.original_name,
            genre_ids = it.genre_ids,
            name = it.name,
            popularity = it.popularity,
            origin_country = it.origin_country,
            vote_count = it.vote_count,
            first_air_date = it.first_air_date,
            backdrop_path = it.backdrop_path,
            original_language = it.original_language,
            vote_average = it.vote_average,
            overview = it.overview,
            poster_path = it.poster_path
        )
    }
}