package com.example.popularmoviesapp.repository.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.popularmoviesapp.repository.local.entities.LocalGenre
import com.example.popularmoviesapp.repository.local.entities.LocalMovieGenre
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {
    @Query("SELECT * FROM genres WHERE genre_id= :id")
    fun getGenre(id: Int): Flow<LocalGenre?>

    @Query("SELECT * FROM genres")
    fun getGenres(): Flow<List<LocalGenre>>

    @Query(
        "SELECT genres.name FROM genres INNER JOIN movie_genres ON genres.genre_id = movie_genres.genre_id WHERE movie_genres.movie_id = :movie_id"
    )
    fun getMovieGenreNames(movie_id: Int): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<LocalGenre>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenres(movieGenres: List<LocalMovieGenre>): List<Long>
}