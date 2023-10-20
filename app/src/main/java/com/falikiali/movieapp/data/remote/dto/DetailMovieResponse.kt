package com.falikiali.movieapp.data.remote.dto

import com.falikiali.movieapp.domain.model.DetailMovie
import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(
    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("backdrop_path")
    val backdropPath: String,

    @field:SerializedName("release_date")
    val releaseDate: String,

    @field:SerializedName("id")
    val id: Int
) {
    fun toDomain(): DetailMovie {
        return DetailMovie(
            overview,
            title,
            backdropPath,
            releaseDate,
            id
        )
    }
}
