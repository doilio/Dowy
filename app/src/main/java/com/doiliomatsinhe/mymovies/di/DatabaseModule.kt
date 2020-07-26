package com.doiliomatsinhe.mymovies.di

import android.content.Context
import androidx.room.Room
import com.doiliomatsinhe.mymovies.data.db.MoviesDao
import com.doiliomatsinhe.mymovies.data.db.MoviesDatabase
import com.doiliomatsinhe.mymovies.utils.MOVIE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun providesMovieDao(database: MoviesDatabase): MoviesDao {
        return database.moviesDao
    }

    @Provides
    @Singleton
    fun providesMoviesDatabase(@ApplicationContext appContext: Context): MoviesDatabase {
        return Room.databaseBuilder(
            appContext,
            MoviesDatabase::class.java,
            MOVIE_DATABASE
        ).fallbackToDestructiveMigration()
            .build()
    }


}