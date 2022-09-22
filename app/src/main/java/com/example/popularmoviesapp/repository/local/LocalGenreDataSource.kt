package com.example.popularmoviesapp.repository.local

import com.example.popularmoviesapp.repository.data.genre.GenreDataSource
import com.example.popularmoviesapp.repository.local.daos.GenreDao
import com.example.popularmoviesapp.repository.local.entities.LocalMovieGenre
import com.example.popularmoviesapp.service.model.Genre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalGenreDataSource @Inject constructor(
    private val genreDao: GenreDao,
) : GenreDataSource {

    override suspend fun getGenre(genreId: Int): Flow<Genre?> {
        return genreDao.getGenre(genreId)
            .map {
                it?.toDomain()
            }
    }

    override suspend fun getGenres(): Flow<List<Genre?>> {
        return genreDao.getGenres().map {
            it.map { localGenre -> localGenre.toDomain() }
        }

    }

    override suspend fun getMovieGenres(movie_id: Int): Flow<List<String?>> {
        return genreDao.getMovieGenreNames(movie_id)
    }

    override suspend fun saveGenres(genres: Flow<List<Genre?>>) {
        genres.collect {
            genreDao.insertGenres(it.mapNotNull { genre -> genre?.toLocal() })
        }
    }

    override suspend fun saveMovieGenres(movie_id: Int, genre_ids: List<Int>) {
        val movieGenres = genre_ids.map { genre_id ->
            LocalMovieGenre(movie_id, genre_id)
        }
        genreDao.insertMovieGenres(movieGenres)
    }
}