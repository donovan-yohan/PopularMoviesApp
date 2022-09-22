package com.example.popularmoviesapp.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.popularmoviesapp.service.GenreService
import com.example.popularmoviesapp.service.MovieService
import com.example.popularmoviesapp.service.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieService: MovieService,
    private val genreService: GenreService
) :
    ViewModel() {
    var movie: Flow<Movie?> = emptyFlow()
    var genres: Flow<List<String?>> = emptyFlow()

    init {
        movie = getMovieById(null)
        genres = getGenres(null)
    }

    fun getMovieById(movieId: Int?): Flow<Movie?> {
        if (movieId != null) {
            viewModelScope.launch {
                movie = movieService.getMovieById(movieId)
            }
        }
        return movie
    }

    fun getGenres(movieId: Int?): Flow<List<String?>> {
        if (movieId != null) {
            viewModelScope.launch {
                genres = genreService.getMovieGenres(movieId)
            }
        }
        return genres
    }
}

