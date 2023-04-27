package com.moviedetails.presentation.ui

import com.mhmd.core.domain.Queue
import com.mhmd.core.domain.UIComponent
import com.moviedetails.domain.MovieDetails


data class MovieDetailsState(
    val movieDetails: MovieDetails? = null,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf())
)
