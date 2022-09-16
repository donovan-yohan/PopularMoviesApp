package com.example.popularmoviesapp.repository.network.model

import com.example.popularmoviesapp.service.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdrop_path: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val poster_path: String?,
    @SerializedName("release_date") val release_date: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("vote_average") val vote_average: Float?,
    @SerializedName("vote_count") val vote_count: Int?,
) {
    fun toDomain(page: Int) =
        Movie(
            id,
            adult,
            backdrop_path,
            null,
            overview,
            poster_path,
            release_date,
            null,
            null,
            title,
            vote_average,
            vote_count,
            page
        )
}