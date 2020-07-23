package com.doiliomatsinhe.mymovies.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.doiliomatsinhe.mymovies.model.Movie
import com.doiliomatsinhe.mymovies.model.TvSeries

@Entity(tableName = "movies")
data class DatabaseMovie(

    @PrimaryKey
    val id: Int,
    val popularity: Double,
    val vote_count: Int,
    val video: Boolean,
    val poster_path: String,
    val adult: Boolean,
    val backdrop_path: String,
    val original_language: String,
    val original_title: String,
    val genre_ids: List<Int>,
    val title: String,
    val vote_average: Double,
    val overview: String,
    val release_date: String,
    val fullPosterPath: String
)

@Entity(tableName = "series")
data class DatabaseSeries(

    @PrimaryKey
    val id: Int,
    val original_name: String,
    val genre_ids: List<Int>,
    val name: String,
    val popularity: Double,
    val origin_country: List<String>,
    val vote_count: Int,
    val first_air_date: String,
    val backdrop_path: String,
    val original_language: String,
    val vote_average: Double,
    val overview: String,
    val poster_path: String,
    val fullPosterPath: String
)

fun List<Movie>.asDatabaseModel(): Array<DatabaseMovie> {
    return map {
        DatabaseMovie(
            popularity = it.popularity,
            vote_count = it.vote_count,
            video = it.video,
            poster_path = it.poster_path,
            id = it.id,
            adult = it.adult,
            backdrop_path = it.backdrop_path,
            original_language = it.original_language,
            original_title = it.original_title,
            genre_ids = it.genre_ids,
            title = it.title,
            vote_average = it.vote_average,
            overview = it.overview,
            release_date = it.release_date,
            fullPosterPath = it.fullPosterPath
        )
    }.toTypedArray()
}

fun List<TvSeries>.asDatabaseModel(): Array<DatabaseSeries> {
    return map {
        DatabaseSeries(
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
            poster_path = it.poster_path,
            fullPosterPath = it.fullPosterPath
        )
    }.toTypedArray()
}





