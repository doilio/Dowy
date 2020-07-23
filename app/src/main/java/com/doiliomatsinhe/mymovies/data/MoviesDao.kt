package com.doiliomatsinhe.mymovies.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.doiliomatsinhe.mymovies.data.DatabaseMovie
import com.doiliomatsinhe.mymovies.data.DatabaseSeries

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun getMovies(): LiveData<List<DatabaseMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(vararg movie: DatabaseMovie)

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id: Int): LiveData<DatabaseMovie>

    @Query("SELECT * FROM series")
    fun getSeries(): LiveData<List<DatabaseSeries>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSeries(vararg series: DatabaseSeries)

    @Query("SELECT * FROM series WHERE id = :id")
    fun getSeriesById(id: Int): LiveData<DatabaseSeries>

}