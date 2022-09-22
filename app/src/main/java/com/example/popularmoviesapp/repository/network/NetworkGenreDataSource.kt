package com.example.popularmoviesapp.repository.network

import android.content.Context
import com.example.popularmoviesapp.repository.data.genre.GenreDataSource
import com.example.popularmoviesapp.service.model.Genre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkGenreDataSource @Inject constructor(
    private val genresApi: GenresApi,
    private val context: Context
) :
    GenreDataSource {
    override suspend fun getGenres(): Flow<List<Genre>> {
        return if (NetworkCapabilitiesManager.hasNetworkCapabilities(context)) {
            try {
                val genreData = genresApi.getGenres()
                flow { emit(genreData.genres.map { it.toDomain() }) }
            } catch (e: Exception) {
                emptyFlow()
            }
        } else {
            emptyFlow()
        }
    }

    // N/A for network
    override suspend fun getMovieGenres(movie_id: Int): Flow<List<String?>> {
        return emptyFlow()
    }

    override suspend fun getGenre(genreId: Int): Flow<Genre?> {
        return emptyFlow()
    }

    override suspend fun saveGenres(genres: Flow<List<Genre?>>) {}

    override suspend fun saveMovieGenres(movie_id: Int, genre_ids: List<Int>) {}
}