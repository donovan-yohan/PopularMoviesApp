package com.example.popularmoviesapp.repository.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.popularmoviesapp.service.model.Movie

@Entity(
    tableName = "movies",
    indices = [Index(value = ["id"], unique = true)]
)
data class LocalMovie(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val adult: Boolean?,
    val backdrop_path: String?,
    val budget: Long?,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val revenue: Long?,
    val runtime: Int?,
    val title: String?,
    val vote_average: Float?,
    val vote_count: Int?,
    var page: Int = 0,
) {
    fun toDomain() = Movie(
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