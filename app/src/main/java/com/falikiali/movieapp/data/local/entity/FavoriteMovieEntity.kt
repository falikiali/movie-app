package com.falikiali.movieapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.falikiali.movieapp.domain.model.FavoriteMovie

@Entity(tableName = "tbl_favorite_movie")
data class FavoriteMovieEntity(
    @PrimaryKey @ColumnInfo("id") val id: Int,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("image") val image: String,
    @ColumnInfo("overview") val overview: String,
    @ColumnInfo("release_date") val releaseDate: String
) {
    fun toDomain(): FavoriteMovie {
        return FavoriteMovie(
            title,
            image,
            releaseDate,
            overview,
            id
        )
    }
}
