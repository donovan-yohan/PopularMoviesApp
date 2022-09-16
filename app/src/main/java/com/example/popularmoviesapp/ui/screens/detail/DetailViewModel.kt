package com.example.popularmoviesapp.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.popularmoviesapp.service.MovieService
import com.example.popularmoviesapp.service.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieService: MovieService) :
    ViewModel() {
    var movie: Flow<Movie?> = emptyFlow()

    init {
        movie = getMovieById(null)
    }

    fun getMovieById(movieId: Int?): Flow<Movie?> {
        if (movieId != null) {
            viewModelScope.launch {
                movie = movieService.getMovieById(movieId)
                    .distinctUntilChanged()
                    .map {
                        it
                    }
            }
        }
        return movie
    }
}

