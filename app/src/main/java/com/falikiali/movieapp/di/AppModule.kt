package com.falikiali.movieapp.di

import com.falikiali.movieapp.domain.usecase.ImplMovieUseCase
import com.falikiali.movieapp.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideMovieUseCase(implMovieUseCase: ImplMovieUseCase): MovieUseCase

}