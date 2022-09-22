package com.example.popularmoviesapp.repository.network.model

import com.example.popularmoviesapp.service.model.Genre
import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
) {
    fun toDomain() = Genre(id, name)
}