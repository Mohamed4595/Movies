package com.moviedetails.data.network

object EndPoints {
    fun movieDetails(movieId: Long) = "movie/$movieId"
    fun movieVideos(movieId: Long) = "movie/$movieId/videos"

    fun recommendationsMovies(movieId: Long) = "movie/$movieId/recommendations"

    fun similarMovies(movieId: Long) = "movie/$movieId/similar"

}