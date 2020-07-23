package com.doiliomatsinhe.mymovies.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.doiliomatsinhe.mymovies.model.Movie

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

fun List<DatabaseMovie>.asDomainModel(): List<Movie> {

    return map {
        Movie(
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
            release_date = it.release_date
        )
    }
}

fun LiveData<DatabaseMovie>.asDomainModel(): LiveData<Movie>{
    return map {
        Movie(
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
            release_date = it.release_date
        )
    }
}