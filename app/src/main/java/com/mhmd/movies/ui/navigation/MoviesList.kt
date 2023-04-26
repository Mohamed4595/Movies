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
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import coil.ImageLoader
import com.google.accompanist.navigation.animation.composable
import com.moviesList.presentation.ui.MoviesListScreen
import com.moviesList.presentation.ui.MoviesListViewModel

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addMoviesList(
    navController: NavController,
    imageLoader: ImageLoader,
    width: Int,
) {
    composable(
        route = Screen.MoviesList.route,
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
    ) {
        val viewModel: MoviesListViewModel = hiltViewModel()
        MoviesListScreen(
            state = viewModel.state.value,
            events = viewModel::onTriggerEvent,
            navigateToDetailScreen = {
            },
            imageLoader = imageLoader,
        )
    }
}
