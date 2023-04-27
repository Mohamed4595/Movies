package com.moviesList.presentation.ui

import com.mhmd.core.domain.UIComponent
import com.moviesList.domain.MoviesFilter

sealed class MoviesListEvents {

    object GetMovies : MoviesListEvents()
    object GetNextPageMovies : MoviesListEvents()
    object OnRemoveHeadFromQueue : MoviesListEvents()
    data class OnSelectMoviesFilter(
        val moviesFilter: MoviesFilter
    ) : MoviesListEvents()
}
