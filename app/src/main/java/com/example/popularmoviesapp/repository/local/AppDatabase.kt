package com.example.popularmoviesapp.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.popularmoviesapp.repository.local.daos.GenreDao
import com.example.popularmoviesapp.repository.local.daos.MovieDao
import com.example.popularmoviesapp.repository.local.entities.LocalGenre
import com.example.popularmoviesapp.repository.local.entities.LocalMovie
import com.example.popularmoviesapp.repository.local.entities.LocalMovieGenre

@Database(
    entities = [
        LocalMovie::class,
        LocalGenre::class,
        LocalMovieGenre::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(MovieDataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
}