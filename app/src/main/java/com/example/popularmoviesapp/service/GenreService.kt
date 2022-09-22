package com.example.popularmoviesapp.service

import com.example.popularmoviesapp.repository.data.genre.GenreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenreService @Inject constructor(private val genresRepo: GenreRepository) {
    suspend fun getMovieGenres(movie_id: Int): Flow<List<String?>> {
        return genresRepo.getMovieGenres(movie_id)
    }
}