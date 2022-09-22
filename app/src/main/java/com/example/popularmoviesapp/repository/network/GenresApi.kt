package com.example.popularmoviesapp.repository.network

import com.example.popularmoviesapp.repository.network.model.GenreListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresApi {
    @GET(ApiConstants.MOVIE_GENRES)
    suspend fun getGenres(
        @Query("language") language: String = "en-US",
    ): GenreListDto
}