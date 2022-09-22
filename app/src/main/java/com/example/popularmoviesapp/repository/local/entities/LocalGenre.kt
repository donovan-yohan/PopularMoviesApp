package com.example.popularmoviesapp.repository.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.popularmoviesapp.service.model.Genre

@Entity(
    tableName = "genres",
    indices = [Index(value = ["genre_id"], unique = true)]
)
data class LocalGenre(
    @PrimaryKey(autoGenerate = true) val genre_id: Int,
    val name: String?,
) {
    fun toDomain() = Genre(
        genre_id,
        name
    )
}