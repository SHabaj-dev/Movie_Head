package com.sbz.moviehead.api

import com.sbz.moviehead.model.MovieDetails
import com.sbz.moviehead.model.ResponseMoviesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<ResponseMoviesModel>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): Response<ResponseMoviesModel>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int): Response<ResponseMoviesModel>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Int): Response<MovieDetails>
}