package com.example.popularmoviesapp.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.popularmoviesapp.repository.local.daos.MovieDao
import com.example.popularmoviesapp.repository.local.model.LocalMovie

@Database(
    entities = [
        LocalMovie::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}