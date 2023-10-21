package com.falikiali.movieapp.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.falikiali.movieapp.domain.model.DetailMovie
import com.falikiali.movieapp.domain.model.FavoriteMovie
import com.falikiali.movieapp.domain.model.Movie
import com.falikiali.movieapp.domain.repository.MovieRepository
import com.falikiali.movieapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImplMovieUseCase @Inject constructor(private val movieRepository: MovieRepository) : MovieUseCase {
    /**
     * Remote
     */
    override fun getMovies(): Flow<PagingData<Movie>> {
        return movieRepository.getMovies().map { data ->
            data.map { it.toDomain() }
        }
    }

    override fun searchMovie(query: String): Flow<PagingData<Movie>> {
        return movieRepository.searchMovie(query).map { data ->
            data.map { it.toDomain() }
        }
    }

    override suspend fun getDetailMovie(id: Int): Flow<ResultState<DetailMovie>> {
        return movieRepository.getDetailMovie(id)
    }

    /**
     * Local
     */
    override fun getAllFavoriteMovie(): Flow<List<FavoriteMovie>> {
        return movieRepository.getAllFavoriteMovie()
    }

    override fun checkFavoriteMovie(id: Int): Flow<Boolean> {
        return movieRepository.checkFavoriteMovie(id)
    }

    override suspend fun addToFavorite(favoriteMovie: FavoriteMovie) {
        return movieRepository.addToFavorite(favoriteMovie)
    }

    override suspend fun removeFavorite(favoriteMovie: FavoriteMovie) {
        movieRepository.removeFavorite(favoriteMovie)
    }
}