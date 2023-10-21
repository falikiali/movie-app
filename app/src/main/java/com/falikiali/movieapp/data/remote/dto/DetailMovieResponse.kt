package com.falikiali.movieapp.data.remote.dto

import com.falikiali.movieapp.domain.model.DetailMovie
import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(
    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("genres")
    val genres: List<GenresDetailMovieResponse>? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null
) {
    fun toDomain(): DetailMovie {
        return DetailMovie(
            overview,
            title,
            backdropPath,
            releaseDate,
            id,
            genres?.map { it.name },
            posterPath
        )
    }
}

data class GenresDetailMovieResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String
)
