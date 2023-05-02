package com.moviedetails.data.network

import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.Pagination
import com.moviedetails.domain.MovieDetails
import com.moviedetails.domain.Video
import com.moviesList.domain.Movie

interface MovieDetailsService {

    suspend fun getMovieDetails(movieId: Long): ApiResponse<MovieDetails>

    suspend fun getMovieVideos(movieId: Long): ApiResponse<List<Video>>
    suspend fun getRecommendationsMovies(movieId: Long,page:Int): ApiResponse<Pagination<Movie>>

    suspend fun getSimilarMovies(movieId: Long,page:Int): ApiResponse<Pagination<Movie>>

}