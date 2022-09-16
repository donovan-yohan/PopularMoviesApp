package com.example.popularmoviesapp.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.popularmoviesapp.service.MovieService
import com.example.popularmoviesapp.service.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val movieService: MovieService) :
    ViewModel() {
    var movieList: Flow<PagingData<Movie>>
    private var movieListScrollIndex = 0

    init {
        movieList = loadMovies()
    }

    private fun loadMovies(): Flow<PagingData<Movie>> {
        var movieListFromRepo: Flow<PagingData<Movie>> = emptyFlow()
        viewModelScope.launch {
            movieListFromRepo = movieService.getMostPopularMovies()
                .cachedIn(viewModelScope)
        }
        return movieListFromRepo
    }

    fun reloadMovies() {
        viewModelScope.launch {
            movieService.reloadMovies()
        }
    }

    fun saveMovieListScrollIndex(scrollIndex: Int) {
        movieListScrollIndex = scrollIndex
    }

    fun getMovieListScrollIndex(): Int {
        return movieListScrollIndex
    }
}

