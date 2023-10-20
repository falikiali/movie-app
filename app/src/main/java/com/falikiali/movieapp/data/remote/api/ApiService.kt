package com.falikiali.movieapp.data.remote.api

import com.falikiali.movieapp.data.remote.dto.DetailMovieResponse
import com.falikiali.movieapp.data.remote.dto.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    suspend fun getMovies(@Query("page") page: Int): MovieResponse

    @GET("search/movie")
    suspend fun searchMovie(@Query("page") page: Int, @Query("query") query: String): MovieResponse

    @GET("movie/{id}")
    suspend fun getDetailMovie(@Path("id") id: Int): Response<DetailMovieResponse>

}