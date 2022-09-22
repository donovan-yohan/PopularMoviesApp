package com.example.popularmoviesapp.service.model

import com.example.popularmoviesapp.repository.local.entities.LocalMovie

data class Movie(
    val id: Int,
    val adult: Boolean?,
    val backdrop_path: String?,
    val budget: Long?,
    val genre_ids: List<Int>?,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val revenue: Long?,
    val runtime: Int?,
    val title: String?,
    val vote_average: Float?,
    val vote_count: Int?,
    var page: Int
) {
    fun toLocal() = LocalMovie(
        id,
        adult,
        backdrop_path,
        budget,
        overview,
        poster_path,
        release_date,
        revenue,
        runtime,
        title,
        vote_average,
        vote_count,
        page
    )
}