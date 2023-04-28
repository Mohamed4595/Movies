package com.moviesList.data.network

import com.mhmd.core.domain.ApiResponse
import com.moviesList.domain.Movie
import com.moviesList.domain.MoviesFilter
import com.mhmd.core.domain.Pagination

interface MoviesService {

    suspend fun getMovies(page: Int = 1,moviesFilter: MoviesFilter): ApiResponse<Pagination<Movie>>

}