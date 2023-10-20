package com.falikiali.movieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falikiali.movieapp.domain.model.DetailMovie
import com.falikiali.movieapp.domain.usecase.MovieUseCase
import com.falikiali.movieapp.utils.ResultState
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailMovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

    private val _detailMovieState = MutableLiveData<ResultState<DetailMovie>>()
    val detailMovieState: LiveData<ResultState<DetailMovie>> get() = _detailMovieState

    fun getDetailMovie(id: Int) {
        viewModelScope.launch {
            movieUseCase.getDetailMovie(id).collect {
                _detailMovieState.value = it
            }
        }
    }

}