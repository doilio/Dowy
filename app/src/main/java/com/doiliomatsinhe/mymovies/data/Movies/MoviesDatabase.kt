package com.doiliomatsinhe.mymovies.data.Movies

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.doiliomatsinhe.mymovies.data.DatabaseMovie
import com.doiliomatsinhe.mymovies.utils.Converters

@Database(entities = [DatabaseMovie::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao

}