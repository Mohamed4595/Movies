package com.moviesList.presentation.ui

import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.Queue
import com.mhmd.core.domain.UIComponent
import com.moviesList.domain.Movie
import com.moviesList.domain.MoviesFilter


data class MoviesListState(
    val movies: List<Movie> = listOf(),
    val isLoadingNextPage: ProgressBarState = ProgressBarState.Idle,
    val selectedMoviesFilter: MoviesFilter = MoviesFilter.POPULAR,
    val page: Int = 1,
    val totalPages: Int = 0,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf())
)
