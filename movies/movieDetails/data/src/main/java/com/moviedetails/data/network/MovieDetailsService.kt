package com.moviedetails.data.network

import com.mhmd.core.domain.ApiResponse
import com.moviedetails.domain.MovieDetails

interface MovieDetailsService {

    suspend fun getMovieDetails(movieId: Long): ApiResponse<MovieDetails>

}