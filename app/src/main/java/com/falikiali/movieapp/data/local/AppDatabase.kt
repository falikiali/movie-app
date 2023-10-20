package com.falikiali.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.falikiali.movieapp.data.local.dao.FavoriteMovieDao
import com.falikiali.movieapp.data.local.entity.FavoriteMovieEntity

@Database(entities = [FavoriteMovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun favoriteMovieDao(): FavoriteMovieDao

}