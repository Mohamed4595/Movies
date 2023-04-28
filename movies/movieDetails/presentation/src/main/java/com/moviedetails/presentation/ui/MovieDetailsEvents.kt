package com.moviedetails.presentation.ui

sealed class MovieDetailsEvents {
    data class GetMovieDetailsData(val movieId: Long) : MovieDetailsEvents()

    object GetNextRecommendationsMovies : MovieDetailsEvents()
    object GetNextSimilarMovies : MovieDetailsEvents()

    object OnRemoveHeadFromQueue : MovieDetailsEvents()


}
