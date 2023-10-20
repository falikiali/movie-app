package com.falikiali.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.falikiali.movieapp.data.local.entity.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM tbl_favorite_movie")
    fun getAll(): Flow<List<FavoriteMovieEntity>>

    @Query("SELECT EXISTS (SELECT * FROM tbl_favorite_movie WHERE id = :id)")
    fun checkFavoriteMovie(id: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(favoriteMovieEntity: FavoriteMovieEntity)

    @Delete
    suspend fun removeFavorite(favoriteMovieEntity: FavoriteMovieEntity): Int
}