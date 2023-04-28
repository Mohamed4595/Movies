package com.moviedetails.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.mhmd.components.DefaultScreenUI
import com.mhmd.components.EmptyView
import com.mhmd.components.GrayTransparentColor
import com.mhmd.components.IconButton
import com.mhmd.components.LightColorSurface
import com.mhmd.components.MovieCard
import com.mhmd.components.R
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.UiState
import com.moviedetails.presentation.components.DurationSection
import com.moviedetails.presentation.components.GenresSection
import com.moviedetails.presentation.components.HorizontalMoviesSection
import com.moviedetails.presentation.components.MovieCover
import com.moviedetails.presentation.components.MovieTitleSection
import com.moviedetails.presentation.components.ProductionCompaniesSection

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MovieDetailsScreen(
    uiState: UiState<MovieDetailsState>,
    events: (MovieDetailsEvents) -> Unit,
    onBack: () -> Unit,
    imageLoader: ImageLoader
) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val listState = rememberLazyListState()

    val transition =
        updateTransition(
            listState.firstVisibleItemIndex != 0,
            label = ""
        )

    val movieCoverSize by transition.animateDp(label = "movieCoverSize") { isScrolling ->
        if (isScrolling) screenHeight / 5 else screenHeight / 3
    }
    val movieCardTopPaddingSize by transition.animateDp(label = "movieCardTopPaddingSize") { isScrolling ->
        if (isScrolling) screenHeight / 15 else screenHeight / 5
    }
    DefaultScreenUI(
        queue = when (uiState) {
            is UiState.Error -> uiState.state.errorQueue
            is UiState.Loading -> uiState.state.errorQueue
            is UiState.Success -> uiState.state.errorQueue
        },
        onRemoveHeadFromQueue = {
            events(MovieDetailsEvents.OnRemoveHeadFromQueue)
        },
        progressBarState = if (uiState is UiState.Loading) uiState.progressBarState else ProgressBarState.Idle,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (uiState is UiState.Error) {
                    EmptyView(message = uiState.errorMessage)
                }
                if (uiState is UiState.Success) {
                    val movieDetails = uiState.state.movieDetails
                    if (movieDetails != null) {
                        AnimatedVisibility(visible = true) {

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    MovieCover(
                                        modifier = Modifier
                                            .height(movieCoverSize),
                                        coverUrl = movieDetails.backdropImage,
                                        imageLoader = imageLoader
                                    )
                                    Divider(
                                        modifier = Modifier.fillMaxWidth(),
                                        color = MaterialTheme.colors.primary,
                                        thickness = 1.dp
                                    )
                                    LazyColumn(
                                        modifier = Modifier.fillMaxHeight(),
                                        state = listState
                                    ) {
                                        item {
                                            MovieTitleSection(
                                                title = movieDetails.title,
                                                date = movieDetails.releaseDate
                                            )
                                        }

                                        item {
                                            Text(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(16.dp),
                                                text = movieDetails.overview,
                                                style = MaterialTheme.typography.body2
                                            )
                                        }

                                        if (movieDetails.genres.isNotEmpty())
                                            item {
                                                GenresSection(genres = movieDetails.genres)
                                            }

                                        item {
                                            DurationSection(duration = movieDetails.runtime)
                                        }


                                        if (movieDetails.productionCompanies.isNotEmpty())
                                            item {
                                                ProductionCompaniesSection(
                                                    productionCompanies = movieDetails.productionCompanies,
                                                    imageLoader = imageLoader
                                                )
                                            }

                                        val similarMovies = uiState.state.paginationSimilarMovies
                                        if (similarMovies != null && !similarMovies.results.isNullOrEmpty())
                                            item {
                                                HorizontalMoviesSection(
                                                    title = stringResource(id = R.string.similar_movies),
                                                    imageLoader = imageLoader,
                                                    movies = similarMovies.results ?: emptyList(),
                                                    canLoadMore = (similarMovies.page
                                                        ?: 0) < (similarMovies.totalPages ?: 0),
                                                    onLoadMore = { events(MovieDetailsEvents.GetNextSimilarMovies) },
                                                    onMovieClicked = {
                                                        events(
                                                            MovieDetailsEvents.GetMovieDetailsData(
                                                                it
                                                            )
                                                        )
                                                    }
                                                )
                                            }

                                        val recommendationsMovies =
                                            uiState.state.paginationRecommendationsMovies
                                        if (recommendationsMovies != null && !recommendationsMovies.results.isNullOrEmpty())
                                            item {
                                                HorizontalMoviesSection(
                                                    title = stringResource(id = R.string.recommendations),
                                                    imageLoader = imageLoader,
                                                    movies = recommendationsMovies.results
                                                        ?: emptyList(),
                                                    canLoadMore = (recommendationsMovies.page
                                                        ?: 0) < (recommendationsMovies.totalPages
                                                        ?: 0),
                                                    onLoadMore = { events(MovieDetailsEvents.GetNextRecommendationsMovies) },
                                                    onMovieClicked = {
                                                        events(
                                                            MovieDetailsEvents.GetMovieDetailsData(
                                                                it
                                                            )
                                                        )
                                                    }
                                                )
                                            }
                                    }
                                }
                            }

                            MovieCard(
                                modifier = Modifier
                                    .padding(start = 16.dp, top = movieCardTopPaddingSize)
                                    .width(screenWidth / 3)
                                    .height(screenHeight / 4),
                                moviePoster = movieDetails.posterImage,
                                voteAverage = movieDetails.voteAverage,
                                onMovieClick = { },
                                imageLoader = imageLoader
                            )
                        }
                    }
                }
            }

            IconButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 8.dp),
                iconRes = R.drawable.ic_arrow_back,
                iconColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.primary
            ) {
                onBack()
            }
        }

    }
}








