package com.example.popularmoviesapp.repository.local.daos

import androidx.paging.PagingSource
import androidx.room.*
import com.example.popularmoviesapp.repository.local.entities.LocalMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT movies.movie_id, title, poster_path, overview, page FROM movies")
    fun getPopularMovies(): Flow<List<LocalMovie>>

    @Query("SELECT movies.movie_id, title, poster_path, overview, page FROM movies ORDER BY page ASC")
    fun getPopularMoviesPagingSource(): PagingSource<Int, LocalMovie>

    @Query("SELECT * FROM movies WHERE movie_id= :movie_id")
    fun getMovieById(movie_id: Int): Flow<LocalMovie?>

    @Transaction
    @Query("DELETE FROM movies")
    suspend fun refreshMovies(movies: List<LocalMovie>): List<Long> {
        return insertMovies(movies)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: LocalMovie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<LocalMovie>): List<Long>

    @Query("SELECT COUNT(movie_id) FROM movies")
    suspend fun countMovies(): Int
}