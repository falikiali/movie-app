package com.falikiali.movieapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.falikiali.movieapp.data.local.dao.FavoriteMovieDao
import com.falikiali.movieapp.data.local.entity.FavoriteMovieEntity
import com.falikiali.movieapp.data.paging.MoviePagingSource
import com.falikiali.movieapp.data.paging.SearchMoviePagingSource
import com.falikiali.movieapp.data.remote.api.ApiService
import com.falikiali.movieapp.data.remote.dto.BaseResponse
import com.falikiali.movieapp.data.remote.dto.ItemMovieResponse
import com.falikiali.movieapp.domain.model.DetailMovie
import com.falikiali.movieapp.domain.model.FavoriteMovie
import com.falikiali.movieapp.domain.repository.MovieRepository
import com.falikiali.movieapp.utils.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject

class ImplMovieRepository @Inject constructor(private val apiService: ApiService, private val favoriteMovieDao: FavoriteMovieDao): MovieRepository {
    /**
     * Remote
     */
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

    override suspend fun getDetailMovie(id: Int): Flow<ResultState<DetailMovie>> {
        return flow {
            emit(ResultState.Loading)
            try {
                val response = apiService.getDetailMovie(id)

                if (response.isSuccessful) {
                    val data = response.body()!!.toDomain()
                    emit(ResultState.Success(data))
                } else {
                    response.errorBody()?.let {
                        val er = Gson().fromJson(it.charStream(), BaseResponse::class.java)
                        emit(ResultState.Failed(er.statusMessage, response.code()))
                    }
                }
            } catch (e: HttpException) {
                emit(ResultState.Failed(e.message(), e.code()))
            } catch (e: Throwable) {
                emit(ResultState.Failed(e.message.toString(), 0))
            }
        }.flowOn(Dispatchers.IO)
    }

    /**
     * Local database
     */
    override fun getAllFavoriteMovie(): Flow<List<FavoriteMovie>> {
        return favoriteMovieDao.getAll().map { list ->
            list.map {
                it.toDomain()
            }
        }
    }

    override fun checkFavoriteMovie(id: Int): Flow<Boolean> {
        return favoriteMovieDao.checkFavoriteMovie(id)
    }

    override suspend fun addToFavorite(favoriteMovie: FavoriteMovie) {
        return favoriteMovieDao.addToFavorite(favoriteMovie.toEntity())
    }

    override suspend fun removeFavorite(favoriteMovie: FavoriteMovie) {
       favoriteMovieDao.removeFavorite(favoriteMovie.toEntity())
    }
}