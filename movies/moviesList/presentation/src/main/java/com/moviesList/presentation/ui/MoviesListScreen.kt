package com.moviesList.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.ImageLoader
import com.mhmd.components.AppBarDivider
import com.mhmd.components.DefaultScreenUI
import com.mhmd.components.MoviesTopAppBar
import com.mhmd.components.PaginatedLazyVerticalGrid
import com.mhmd.components.R
import com.moviesList.domain.Movie
import com.moviesList.presentation.components.MovieListItem
import com.moviesList.presentation.components.MoviesFilter

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
                    isDivider = false
                )
                MoviesFilter(state = state, events = events)
                AppBarDivider()
                AnimatedVisibility(visible = true) {

                    state.movies.PaginatedLazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
                        onLoadMore = {
                            events(MoviesListEvents.GetNextPageMovies)
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
                        isLoading = state.isLoadingNextPage,
                        canLoadMore = state.page <= state.totalPages
                    )
                }
            }

        }
    }
}








