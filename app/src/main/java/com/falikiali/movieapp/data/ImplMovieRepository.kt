package com.falikiali.movieapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.falikiali.movieapp.data.paging.MoviePagingSource
import com.falikiali.movieapp.data.paging.SearchMoviePagingSource
import com.falikiali.movieapp.data.remote.api.ApiService
import com.falikiali.movieapp.data.remote.dto.ItemMovieResponse
import com.falikiali.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImplMovieRepository @Inject constructor(private val apiService: ApiService): MovieRepository {
    override fun getMovies(): Flow<PagingData<ItemMovieResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                prefetchDistance = 1
            ),
            pagingSourceFactory = {
                MoviePagingSource(apiService)
            }
        ).flow
    }

    override fun searchMovie(query: String): Flow<PagingData<ItemMovieResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                prefetchDistance = 1
            ),
            pagingSourceFactory = {
                SearchMoviePagingSource(apiService, query)
            }
        ).flow
    }
}