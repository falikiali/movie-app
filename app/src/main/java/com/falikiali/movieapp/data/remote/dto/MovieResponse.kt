package com.falikiali.movieapp.data.remote.dto

import com.falikiali.movieapp.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("total_pages")
    val totalPages: Int,

    @field:SerializedName("results")
    val results: List<ItemMovieResponse>,

    @field:SerializedName("total_results")
    val totalResults: Int

)

data class ItemMovieResponse(
    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("id")
    val id: Int? = 0,

    @field:SerializedName("overview")
    val overview: String?= null
) {
    fun toDomain(): Movie {
        return Movie(
            title,
            posterPath,
            releaseDate,
            id,
            overview
        )
    }
}
