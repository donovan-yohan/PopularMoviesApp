package com.example.popularmoviesapp.repository.data.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.map
import com.example.popularmoviesapp.repository.data.genre.GenreDataSource
import com.example.popularmoviesapp.repository.data.genre.LocalGenreDataSourceModule
import com.example.popularmoviesapp.repository.network.MovieRemoteMediator
import com.example.popularmoviesapp.service.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    @LocalMovieDataSourceModule
    private val localMovieDataSource: MovieDataSource,
    @NetworkMovieDataSourceModule
    private val networkMovieDataSource: MovieDataSource,
    @LocalGenreDataSourceModule
    private val localGenreDataSource: GenreDataSource,
    dataRefreshManagerImpl: DataRefreshManager
) : MovieRepository {
    @ExperimentalPagingApi
    val movieRemoteMediator = MovieRemoteMediator(
        localMovieDataSource,
        networkMovieDataSource,
        dataRefreshManagerImpl
    )

    @ExperimentalPagingApi
    override suspend fun getMostPopularMovies(): Flow<PagingData<Movie>> {
        val mostPopularMovies =
            localMovieDataSource.getPagedMovies(movieRemoteMediator).map { pagingData ->
                pagingData.map {
                    it.toDomain()
                }
            }
        return mostPopularMovies
    }

    @ExperimentalPagingApi
    override suspend fun reloadMovies() {
        movieRemoteMediator.reload()
    }

    override suspend fun getMovieById(movieId: Int): Flow<Movie?> {
        val fullMovieDataFlow = localMovieDataSource.getMovieById(movieId)
            .map {
                if (it == null || it.revenue == null || it.runtime == null || it.budget == null) {
                    insertNewMovieDetailToLocal(movieId, it?.page)
                }
                it
            }

        return fullMovieDataFlow
    }

    private suspend fun insertNewMovieDetailToLocal(movieId: Int, page: Int?) {
        val movieDetailFlow = networkMovieDataSource.getMovieById(movieId)
        localMovieDataSource.insertMovie(movieDetailFlow, page ?: 0)
    }
}