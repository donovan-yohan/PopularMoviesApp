package com.example.popularmoviesapp.repository.data.genre

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    @LocalGenreDataSourceModule
    private val localGenreDataSource: GenreDataSource,
    @NetworkGenreDataSourceModule
    private val networkGenreDataSource: GenreDataSource,
) : GenreRepository {
    override suspend fun getMovieGenres(movie_id: Int): Flow<List<String?>> {
        val fullGenreDataFlow = localGenreDataSource.getMovieGenres(movie_id)
            .map {
                if (it.isEmpty()) {
                    insertNewGenresToLocal()
                }
                it
            }
        return fullGenreDataFlow
    }

    private suspend fun insertNewGenresToLocal() {
        val genreFlow = networkGenreDataSource.getGenres()
        localGenreDataSource.saveGenres(genreFlow)
    }

}