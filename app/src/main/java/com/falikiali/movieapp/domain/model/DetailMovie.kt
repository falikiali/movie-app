package com.falikiali.movieapp.domain.model

data class DetailMovie(
    val overview: String? = null,
    val title: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String? = null,
    val id: Int? = null,
    val genres: List<String>? = null,
    val posterPath: String? = null
)
