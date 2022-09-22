package com.example.popularmoviesapp.repository.network.model

import com.google.gson.annotations.SerializedName

data class GenreListDto(
    @SerializedName("genres") val genres: List<GenreDto>
)