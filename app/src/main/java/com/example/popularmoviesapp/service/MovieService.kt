package com.example.popularmoviesapp.service

import androidx.paging.PagingData
import com.example.popularmoviesapp.repository.data.movie.MovieRepository
import com.example.popularmoviesapp.service.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieService @Inject constructor(private val moviesRepo: MovieRepository) {
    suspend fun getMostPopularMovies(): Flow<PagingData<Movie>> {
        return moviesRepo.getMostPopularMovies()
    }

    suspend fun getMovieById(id: Int): Flow<Movie?> {
        return moviesRepo.getMovieById(id)
    }

    suspend fun reloadMovies() {
        moviesRepo.reloadMovies()
    }
}