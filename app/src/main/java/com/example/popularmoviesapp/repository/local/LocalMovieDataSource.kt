package com.example.popularmoviesapp.repository.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.popularmoviesapp.repository.MovieDataSource
import com.example.popularmoviesapp.repository.local.daos.MovieDao
import com.example.popularmoviesapp.repository.local.model.LocalMovie
import com.example.popularmoviesapp.repository.network.ApiConstants.PAGE_RESULTS
import com.example.popularmoviesapp.repository.network.MovieRemoteMediator
import com.example.popularmoviesapp.service.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalMovieDataSource @Inject constructor(
    private val movieDao: MovieDao,
) : MovieDataSource {
    @ExperimentalPagingApi
    override suspend fun getPagedMovies(moviesRemoteMediator: MovieRemoteMediator): Flow<PagingData<LocalMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_RESULTS,
                enablePlaceholders = true
            ),
            remoteMediator = moviesRemoteMediator,
            pagingSourceFactory = {
                movieDao.getPopularMoviesPagingSource()
            }
        ).flow
    }

    override suspend fun addNewPopularMovies(movies: List<Movie>) {
        val dbMovies = mutableListOf<LocalMovie>()

        var localMovie: LocalMovie
        for (movie in movies) {
            localMovie = LocalMovie(
                movie.id,
                movie.adult,
                movie.backdrop_path,
                movie.budget,
                movie.overview,
                movie.poster_path,
                movie.release_date,
                movie.revenue,
                movie.runtime,
                movie.title,
                movie.vote_average,
                movie.vote_count,
                movie.page
            )
            dbMovies.add(localMovie)
        }
        movieDao.insertMovies(dbMovies)
    }

    override suspend fun countMovies(): Int {
        return movieDao.countMovies()
    }

    override suspend fun getMovieById(movieId: Int): Flow<Movie?> {
        return movieDao.getMovieById(movieId)
            .map {
                it?.toDomain()
            }
    }

    override suspend fun insertMovie(movieDetail: Flow<Movie?>, page: Int) {
        movieDetail.collect {
            if (it !== null) {
                it.page = page
                movieDao.insertMovie(it.toLocal())
            }
        }
    }

    override suspend fun refreshMovies(movies: List<Movie>) {
        val dbMovies = mutableListOf<LocalMovie>()

        var localMovie: LocalMovie
        for (movie in movies) {
            localMovie = LocalMovie(
                movie.id,
                movie.adult,
                movie.backdrop_path,
                movie.budget,
                movie.overview,
                movie.poster_path,
                movie.release_date,
                movie.revenue,
                movie.runtime,
                movie.title,
                movie.vote_average,
                movie.vote_count,
                movie.page
            )
            dbMovies.add(localMovie)
        }
        movieDao.refreshMovies(dbMovies)
    }

    override suspend fun getNewPopularMovies(page: Int): List<Movie> {
        return emptyList()
    }
}