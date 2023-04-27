package com.mhmd.movies.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import coil.ImageLoader
import com.google.accompanist.navigation.animation.composable
import com.mhmd.constants.NavigationArgumentsConstants
import com.moviedetails.presentation.ui.MovieDetailsScreen
import com.moviedetails.presentation.ui.MovieDetailsViewModel


@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalAnimationApi
fun NavGraphBuilder.addMovieDetails(
    imageLoader: ImageLoader,
    width: Int,
) {
    composable(
        route = Screen.MovieDetails.route + "/{${NavigationArgumentsConstants.MOVIE_ID}}",
        arguments = Screen.MovieDetails.arguments,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        val viewModel: MovieDetailsViewModel = hiltViewModel()
        MovieDetailsScreen(
            uiState = viewModel.state.value,
            events = viewModel::onTriggerEvent,
            imageLoader = imageLoader,
        )
    }
}