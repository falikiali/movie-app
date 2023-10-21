package com.falikiali.movieapp.presentation.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.falikiali.movieapp.domain.model.DetailMovie
import com.falikiali.movieapp.domain.model.FavoriteMovie
import com.falikiali.movieapp.domain.usecase.MovieUseCase
import com.falikiali.movieapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

    private val _detailMovieState = MutableLiveData<ResultState<DetailMovie>>()
    val detailMovieState: LiveData<ResultState<DetailMovie>> get() = _detailMovieState

    private val _isFavorited = MutableLiveData<Boolean>()
    val isFavorited: LiveData<Boolean> get() = _isFavorited

    private val _isAddedToFavorite = MutableLiveData<Boolean>()
    val isAddedToFavorite: LiveData<Boolean> get() = _isAddedToFavorite

    fun getDetailMovie(id: Int) {
        viewModelScope.launch {
            movieUseCase.getDetailMovie(id).collect {
                _detailMovieState.value = it
            }
        }
    }

    fun checkFavoriteMovie(id: Int) {
        viewModelScope.launch {
            movieUseCase.checkFavoriteMovie(id).collect {
                _isFavorited.value = it
            }
        }
    }

    fun updateFavoriteMovie(favoriteMovie: FavoriteMovie) {
        if (_isFavorited.value == true) {
            viewModelScope.launch {
                movieUseCase.removeFavorite(favoriteMovie)
            }
            _isFavorited.value = !_isFavorited.value!!
            _isAddedToFavorite.value = false
        } else {
            viewModelScope.launch {
                movieUseCase.addToFavorite(favoriteMovie)
            }
            _isFavorited.value = !_isFavorited.value!!
            _isAddedToFavorite.value = true
        }
    }

}