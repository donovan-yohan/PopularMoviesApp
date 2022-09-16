package com.example.popularmoviesapp.repository.network

import com.example.popularmoviesapp.repository.network.model.MovieDetailDto
import com.example.popularmoviesapp.repository.network.model.MoviesPageDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET(ApiConstants.POPULAR)
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US",
        @Query("region") region: String? = null
    ): MoviesPageDto

    @GET(ApiConstants.MOVIE)
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): MovieDetailDto
}