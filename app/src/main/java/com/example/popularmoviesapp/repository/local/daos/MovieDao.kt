package com.example.popularmoviesapp.repository.local.daos

import androidx.paging.PagingSource
import androidx.room.*
import com.example.popularmoviesapp.repository.local.model.LocalMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT movies.id, title, poster_path, overview, page FROM movies")
    fun getPopularMovies(): Flow<List<LocalMovie>>

    @Query("SELECT movies.id, title, poster_path, overview, page FROM movies ORDER BY page ASC")
    fun getPopularMoviesPagingSource(): PagingSource<Int, LocalMovie>

    @Query("SELECT * FROM movies WHERE id= :id")
    fun getMovieById(id: Int): Flow<LocalMovie?>

    @Transaction
    suspend fun refreshMovies(movies: List<LocalMovie>): List<Long> {
        return insertMovies(movies)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: LocalMovie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<LocalMovie>): List<Long>

    @Query("SELECT COUNT(id) FROM movies")
    suspend fun countMovies(): Int
}