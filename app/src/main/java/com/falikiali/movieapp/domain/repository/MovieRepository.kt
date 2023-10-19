package com.falikiali.movieapp.domain.repository

import androidx.paging.PagingData
import com.falikiali.movieapp.data.remote.dto.ItemMovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(): Flow<PagingData<ItemMovieResponse>>

}