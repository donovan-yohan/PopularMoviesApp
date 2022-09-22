package com.example.popularmoviesapp.repository.data.genre

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

interface GenreRepository {
    suspend fun getMovieGenres(movie_id: Int): Flow<List<String?>>

}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DependenciesBindings {
    @Singleton
    @Binds
    abstract fun bindRepository(genreRepositoryImpl: GenreRepositoryImpl): GenreRepository
}
