package com.example.popularmoviesapp.repository.network

object ApiConstants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val POPULAR = "movie/popular"
    const val MOVIE = "movie/{movie_id}"

    const val MOVIE_GENRES = "genre/movie/list"

    const val API_PARAM = "api_key"

    // ideally this should be in a keystore, definitely somewhere more secure
    const val API_KEY = "9458f20eaffe3dffdbfae1d73e624a9e"

    const val IMG_BASE_URL = "https://image.tmdb.org/t/p/"
    const val POSTER_W185 = "w185"
    const val POSTER_W342 = "w342"
    const val IMG_SIZE_ORG = "original"

    const val PAGE_START = 1
    const val PAGE_END = 50
    const val PAGES_CACHED = 3
    const val PAGE_RESULTS = 20
}