package com.falikiali.movieapp.domain.model

import com.falikiali.movieapp.data.local.entity.FavoriteMovieEntity

data class FavoriteMovie(
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val id: Int
) {
    fun toEntity(): FavoriteMovieEntity {
        return FavoriteMovieEntity(id, title, posterPath, releaseDate)
    }
}
