package com.moviesList.presentation.ui

import com.mhmd.core.domain.UIComponent

sealed class MoviesListEvents {

    object GetMovies : MoviesListEvents()

    object OnRemoveHeadFromQueue : MoviesListEvents()

    data class Error(
        val uiComponent: UIComponent
    ) : MoviesListEvents()
}
