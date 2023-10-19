package com.falikiali.movieapp.domain.usecase

import androidx.paging.PagingData
import com.falikiali.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    fun getMovies(): Flow<PagingData<Movie>>

}