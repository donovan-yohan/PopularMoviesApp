package com.example.popularmoviesapp.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.popularmoviesapp.repository.local.model.LocalMovie
import com.example.popularmoviesapp.repository.network.MovieRemoteMediator
import com.example.popularmoviesapp.service.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieDataSource {
    @ExperimentalPagingApi
    suspend fun getPagedMovies(moviesRemoteMediator: MovieRemoteMediator): Flow<PagingData<LocalMovie>>

    suspend fun refreshMovies(movies: List<Movie>)

    suspend fun getNewPopularMovies(page: Int): List<Movie>

    suspend fun addNewPopularMovies(movies: List<Movie>)

    suspend fun countMovies(): Int

    suspend fun getMovieById(movieId: Int): Flow<Movie?>

    suspend fun insertMovie(movieDetail: Flow<Movie?>, page: Int)
}