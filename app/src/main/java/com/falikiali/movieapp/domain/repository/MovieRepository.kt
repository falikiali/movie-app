package com.falikiali.movieapp.domain.repository

import androidx.paging.PagingData
import com.falikiali.movieapp.data.remote.dto.ItemMovieResponse
import com.falikiali.movieapp.domain.model.DetailMovie
import com.falikiali.movieapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(): Flow<PagingData<ItemMovieResponse>>

    fun searchMovie(query: String): Flow<PagingData<ItemMovieResponse>>

    suspend fun getDetailMovie(id: Int): Flow<ResultState<DetailMovie>>

}