package com.moviesList.presentation.ui

import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.Queue
import com.mhmd.core.domain.UIComponent
import com.moviesList.domain.Movie


data class MoviesListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val movies: List<Movie> = listOf(),
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf())
)
