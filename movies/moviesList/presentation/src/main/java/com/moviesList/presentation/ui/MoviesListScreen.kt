package com.moviesList.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.mhmd.components.AppBarDivider
import com.mhmd.components.DefaultScreenUI
import com.mhmd.components.EmptyView
import com.mhmd.components.MoviesTopAppBar
import com.mhmd.components.PaginatedLazyVerticalGrid
import com.mhmd.components.R
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.UiState
import com.moviesList.domain.Movie
import com.mhmd.components.MovieCard
import com.moviesList.presentation.components.MoviesFilter

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MoviesListScreen(
    uiState: UiState<MoviesListState>,
    events: (MoviesListEvents) -> Unit,
    navigateToDetailScreen: (Movie) -> Unit,
    imageLoader: ImageLoader
) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    DefaultScreenUI(
        queue = when (uiState) {
            is UiState.Error -> uiState.state.errorQueue
            is UiState.Loading -> uiState.state.errorQueue
            is UiState.Success -> uiState.state.errorQueue
        },
        onRemoveHeadFromQueue = {
            events(MoviesListEvents.OnRemoveHeadFromQueue)
        },
        progressBarState = if (uiState is UiState.Loading) uiState.progressBarState else ProgressBarState.Idle,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column {
                MoviesTopAppBar(
                    title = stringResource(id = R.string.movies),
                    isDivider = uiState !is UiState.Success
                )
                if (uiState is UiState.Error){
                    EmptyView(message = uiState.errorMessage)
                }
                if (uiState is UiState.Success) {
                    MoviesFilter(state = uiState.state, events = events)
                    AppBarDivider()
                    AnimatedVisibility(visible = true) {

                        uiState.state.movies.PaginatedLazyVerticalGrid(
                            modifier = Modifier.fillMaxSize(),
                            onLoadMore = {
                                events(MoviesListEvents.GetNextPageMovies)
                            },
                            content = { item, _ ->
                                MovieCard(
                                    modifier = Modifier.height(screenHeight/3),
                                    moviePoster = item.posterImage,
                                    voteAverage = item.voteAverage,
                                    onMovieClick = {
                                        navigateToDetailScreen(item)
                                    },
                                    imageLoader = imageLoader
                                )
                            },
                            isLoading = uiState.state.isLoadingNextPage,
                            canLoadMore = uiState.state.page <= uiState.state.totalPages
                        )
                    }
                }
            }

        }
    }
}








