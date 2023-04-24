package com.moviesList.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.mhmd.components.DefaultScreenUI
import com.mhmd.components.MoviesTopAppBar
import com.mhmd.components.PaginatedLazyVerticalGrid
import com.mhmd.components.R
import com.moviesList.domain.Movie
import com.moviesList.presentation.components.MovieListItem

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MoviesListScreen(
    state: MoviesListState,
    events: (MoviesListEvents) -> Unit,
    navigateToDetailScreen: (Movie) -> Unit,
    imageLoader: ImageLoader
) {
    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = {
            events(MoviesListEvents.OnRemoveHeadFromQueue)
        },
        progressBarState = state.progressBarState,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column {
                MoviesTopAppBar(
                    title = stringResource(id = R.string.movies),
                )
                AnimatedVisibility(visible = state.movies.isNotEmpty()) {
                    val listState = rememberLazyListState()

                    state.movies.PaginatedLazyVerticalGrid(
                        listState = listState,
                        onLoadMore = {

                        },
                        content = { item, _ ->
                            MovieListItem(
                                movie = item,
                                onMovieClick = {
                                    navigateToDetailScreen(it)
                                },
                                imageLoader = imageLoader
                            )
                        },
                        canLoadMore = false //state.currentPage.value < state.totalPages.value
                    )
                }
            }

        }
    }
}








