package com.moviedetails.presentation.ui

import com.moviedetails.domain.Video
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

sealed class MovieDetailsEvents {
    data class GetMovieDetailsData(val movieId: Long) : MovieDetailsEvents()

    object GetNextRecommendationsMovies : MovieDetailsEvents()
    object GetNextSimilarMovies : MovieDetailsEvents()

    object OnRemoveHeadFromQueue : MovieDetailsEvents()
    data class OnVideoChanged(val flag: Boolean) : MovieDetailsEvents()
    data class SetCurrentVideo(val page: Int,val youTubePlayer: YouTubePlayer) : MovieDetailsEvents()

}
