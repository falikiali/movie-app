package com.falikiali.movieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falikiali.movieapp.domain.model.FavoriteMovie
import com.falikiali.movieapp.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

    private val _resultFavoriteMovie = MutableLiveData<List<FavoriteMovie>>()
    val resultFavoriteMovie: LiveData<List<FavoriteMovie>> get() = _resultFavoriteMovie

    fun getAllFavoriteMovie() {
        viewModelScope.launch {
            movieUseCase.getAllFavoriteMovie().collect {
                _resultFavoriteMovie.value = it
            }
        }
    }

}