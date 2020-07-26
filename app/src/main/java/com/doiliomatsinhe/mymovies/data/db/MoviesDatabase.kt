package com.doiliomatsinhe.mymovies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.doiliomatsinhe.mymovies.utils.Converters

@Database(
    entities = [DatabaseMovie::class, DatabaseSeries::class],
    version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao

}