package com.example.popularmoviesapp.repository.network

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.popularmoviesapp.repository.data.movie.MovieDataSource
import com.example.popularmoviesapp.repository.local.entities.LocalMovie
import com.example.popularmoviesapp.service.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkMovieDataSource @Inject constructor(
    private val moviesApi: MoviesApi,
    private val context: Context
) :
    MovieDataSource {
    override suspend fun getNewPopularMovies(page: Int): List<Movie> {
        return if (NetworkCapabilitiesManager.hasNetworkCapabilities(context)) {
            val popularMovies: List<Movie> = moviesApi.getPopularMovies(page).movieList.map {
                it.toDomain(page)
            }
            popularMovies
        } else {
            emptyList()
        }
    }

    override suspend fun getMovieById(movieId: Int): Flow<Movie?> {
        return if (NetworkCapabilitiesManager.hasNetworkCapabilities(context)) {
            try {
                val movieData = moviesApi.getMovieDetail(movieId)
                flow { emit(movieData.toDomain(0)) }
            } catch (e: Exception) {
                emptyFlow()
            }
        } else {
            emptyFlow()
        }
    }

    // unneeded for network data source
    @ExperimentalPagingApi
    override suspend fun getPagedMovies(moviesRemoteMediator: MovieRemoteMediator): Flow<PagingData<LocalMovie>> {
        return emptyFlow()
    }

    override suspend fun refreshMovies(movies: List<Movie>) {
    }

    override suspend fun insertMovie(movieDetail: Flow<Movie?>, page: Int) {
    }

    override suspend fun addNewPopularMovies(movies: List<Movie>) {
    }

    override suspend fun countMovies(): Int {
        return 0
    }
}