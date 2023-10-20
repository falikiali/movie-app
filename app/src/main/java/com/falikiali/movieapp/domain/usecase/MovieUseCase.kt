package com.falikiali.movieapp.domain.usecase

import androidx.paging.PagingData
import com.falikiali.movieapp.domain.model.DetailMovie
import com.falikiali.movieapp.domain.model.Movie
import com.falikiali.movieapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    fun getMovies(): Flow<PagingData<Movie>>

    fun searchMovie(query: String): Flow<PagingData<Movie>>

    suspend fun getDetailMovie(id: Int): Flow<ResultState<DetailMovie>>

}