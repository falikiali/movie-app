package com.falikiali.movieapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.falikiali.movieapp.data.remote.api.ApiService
import com.falikiali.movieapp.data.remote.dto.ItemMovieResponse
import javax.inject.Inject

class SearchMoviePagingSource(private val apiService: ApiService, private val query: String): PagingSource<Int, ItemMovieResponse>() {
    override fun getRefreshKey(state: PagingState<Int, ItemMovieResponse>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemMovieResponse> {
        return try {
            val page = params.key ?: 1
            val response = apiService.searchMovie(page, query)

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page == response.totalPages) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}