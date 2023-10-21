package com.falikiali.movieapp.domain.model

import com.falikiali.movieapp.data.local.entity.FavoriteMovieEntity

data class FavoriteMovie(
    val title: String? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val overview: String? = null,
    val id: Int
) {
    fun toEntity(): FavoriteMovieEntity {
        return FavoriteMovieEntity(id, title ?: "Unknown", posterPath ?: "-", overview ?: "-", releaseDate ?: "-")
    }
}
