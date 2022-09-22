package com.example.popularmoviesapp.service.model

import com.example.popularmoviesapp.repository.local.entities.LocalGenre

data class Genre(
    val id: Int,
    val name: String?
) {
    fun toLocal() = LocalGenre(
        id,
        name
    )
}