package com.example.popularmoviesapp.repository.local

import android.content.Context
import androidx.room.Room
import com.example.popularmoviesapp.repository.local.daos.GenreDao
import com.example.popularmoviesapp.repository.local.daos.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "db")
            .allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase): MovieDao {
        return db.movieDao()
    }

    @Singleton
    @Provides
    fun provideGenreDao(db: AppDatabase): GenreDao {
        return db.genreDao()
    }
}