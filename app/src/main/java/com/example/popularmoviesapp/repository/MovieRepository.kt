package com.example.popularmoviesapp.repository

import androidx.paging.PagingData
import com.example.popularmoviesapp.service.model.Movie
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

interface MovieRepository {
    suspend fun getMostPopularMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieById(movieId: Int): Flow<Movie?>
    suspend fun reloadMovies()
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DependenciesBindings {
    @Singleton
    @Binds
    abstract fun bindRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}
