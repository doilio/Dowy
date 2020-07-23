package com.doiliomatsinhe.mymovies.data.Movies

import androidx.lifecycle.LiveData
import androidx.room.*
import com.doiliomatsinhe.mymovies.data.DatabaseMovie

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun getMovies(): LiveData<List<DatabaseMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(vararg movie: DatabaseMovie)

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id: Int): LiveData<DatabaseMovie>

}