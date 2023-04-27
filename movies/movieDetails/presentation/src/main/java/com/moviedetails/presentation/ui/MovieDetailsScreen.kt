package com.moviedetails.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.mhmd.components.DefaultScreenUI
import com.mhmd.components.EmptyView
import com.mhmd.components.MovieCard
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.UiState
import com.mhmd.core.util.getTranslatedDateWithMonthNameAndDayName
import com.moviedetails.presentation.components.GenresSection
import com.moviedetails.presentation.components.MovieCover
import com.moviedetails.presentation.components.ProductionCompaniesSection

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MovieDetailsScreen(
    uiState: UiState<MovieDetailsState>,
    events: (MovieDetailsEvents) -> Unit,
    imageLoader: ImageLoader
) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    DefaultScreenUI(
        progressBarState = if (uiState is UiState.Loading) uiState.progressBarState else ProgressBarState.Idle,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column {
                if (uiState is UiState.Error) {
                    EmptyView(message = uiState.errorMessage)
                }
                if (uiState is UiState.Success) {

                    AnimatedVisibility(visible = true) {

                        Box {


                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                MovieCover(
                                    coverUrl = uiState.state.movieDetails?.backdropImage,
                                    imageLoader = imageLoader
                                )
                                Column(
                                    modifier = Modifier
                                        .padding(start = (screenWidth / 3) + 24.dp)
                                        .fillMaxWidth()
                                        .height(screenHeight / 9)
                                ) {
                                    Text(
                                        text = uiState.state.movieDetails?.title ?: "",
                                        style = MaterialTheme.typography.body1,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    uiState.state.movieDetails?.releaseDate?.let {
                                        Text(
                                            text = getTranslatedDateWithMonthNameAndDayName(it),
                                            style = MaterialTheme.typography.overline
                                        )
                                    }

                                }

                                Column {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        text = uiState.state.movieDetails?.overview ?: "",
                                        style = MaterialTheme.typography.body2
                                    )
                                    if (uiState.state.movieDetails?.genres?.isNotEmpty() != null)
                                        GenresSection(genres = uiState.state.movieDetails!!.genres)

                                    if (uiState.state.movieDetails?.productionCompanies?.isNotEmpty() != null)
                                        ProductionCompaniesSection(
                                            productionCompanies = uiState.state.movieDetails!!.productionCompanies,
                                            imageLoader = imageLoader
                                        )
                                }
                            }

                            MovieCard(
                                modifier = Modifier
                                    .padding(start = 16.dp, top = screenHeight / 15)
                                    .width(screenWidth / 3)
                                    .height(screenHeight / 4),
                                moviePoster = uiState.state.movieDetails?.posterImage ?: "",
                                voteAverage = uiState.state.movieDetails?.voteAverage,
                                onMovieClick = { },
                                imageLoader = imageLoader
                            )
                        }
                    }
                }
            }

        }
    }
}








