package com.moviedetails.presentation.ui

import com.mhmd.core.domain.Pagination
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.Queue
import com.mhmd.core.domain.UIComponent
import com.moviedetails.domain.MovieDetails
import com.moviedetails.domain.Video
import com.moviesList.domain.Movie
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer


data class MovieDetailsState(
    val movieDetails: MovieDetails? = null,
    val movieVideos:List<Video> = emptyList(),
    val initializedYouTubePlayer: Map<Int, YouTubePlayer> = emptyMap(),
    val isVideoChanged:Boolean=false,
    val paginationRecommendationsMovies: Pagination<Movie>? = null,
    val paginationSimilarMovies: Pagination<Movie>?= null,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf())
)
