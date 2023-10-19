package com.falikiali.movieapp.di

import com.falikiali.movieapp.data.ImplMovieRepository
import com.falikiali.movieapp.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(implMovieRepository: ImplMovieRepository): MovieRepository

}