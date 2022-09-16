package com.example.popularmoviesapp.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.map
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
    private val dataRefreshManagerImpl: DataRefreshManager
) : MovieRepository {
    @ExperimentalPagingApi
    val movieRemoteMediator = MovieRemoteMediator(
        localMovieDataSource,
        networkMovieDataSource,
        dataRefreshManagerImpl
    )

    @ExperimentalPagingApi
    override suspend fun getMostPopularMovies(): Flow<PagingData<Movie>> {
        if (dataRefreshManagerImpl.shouldRefresh()) {
            reloadMovies()
        }
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
                if (it == null || it.revenue == null || it.runtime == null || it.budget == null || dataRefreshManagerImpl.shouldRefresh()) {
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