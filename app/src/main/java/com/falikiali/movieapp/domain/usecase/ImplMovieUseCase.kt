package com.falikiali.movieapp.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.falikiali.movieapp.domain.model.DetailMovie
import com.falikiali.movieapp.domain.model.Movie
import com.falikiali.movieapp.domain.repository.MovieRepository
import com.falikiali.movieapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImplMovieUseCase @Inject constructor(private val movieRepository: MovieRepository) : MovieUseCase {
    override fun getMovies(): Flow<PagingData<Movie>> {
        return movieRepository.getMovies().map { data ->
            data.map {
                Movie(
                    it.title,
                    it.backdropPath,
                    it.releaseDate,
                    it.id
                )
            }
        }
    }

    override fun searchMovie(query: String): Flow<PagingData<Movie>> {
        return movieRepository.searchMovie(query).map { data ->
            data.map {
                Movie(
                    it.title,
                    it.backdropPath,
                    it.releaseDate,
                    it.id
                )
            }
        }
    }

    override suspend fun getDetailMovie(id: Int): Flow<ResultState<DetailMovie>> {
        return movieRepository.getDetailMovie(id)
    }
}