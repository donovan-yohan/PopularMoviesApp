package com.example.popularmoviesapp.repository.data.genre

import com.example.popularmoviesapp.service.model.Genre
import kotlinx.coroutines.flow.Flow

interface GenreDataSource {
    suspend fun getGenre(genreId: Int): Flow<Genre?>

    suspend fun getGenres(): Flow<List<Genre?>>

    suspend fun getMovieGenres(movie_id: Int): Flow<List<String?>>

    suspend fun saveGenres(genres: Flow<List<Genre?>>)

    suspend fun saveMovieGenres(movie_id: Int, genre_ids: List<Int>)
}