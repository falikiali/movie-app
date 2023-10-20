package com.falikiali.movieapp.presentation.search

import androidx.lifecycle.ViewModel
import com.falikiali.movieapp.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

    private val _movieQuery = MutableStateFlow("")

    val movie = _movieQuery.flatMapLatest { movieUseCase.searchMovie(it) }

    fun searchMovie(query: String?) {
        _movieQuery.value = query ?: ""
    }

}