package com.falikiali.movieapp.domain.model

data class DetailMovie(
    val overview: String,
    val title: String,
    val backdropPath: String,
    val releaseDate: String,
    val id: Int,
    val genres: List<String>
)
