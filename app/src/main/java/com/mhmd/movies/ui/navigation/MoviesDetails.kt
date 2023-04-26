package com.mhmd.movies.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import coil.ImageLoader
import com.google.accompanist.navigation.animation.composable
import com.moviesdetails.presentation.MoviesDetails


@ExperimentalAnimationApi
fun NavGraphBuilder.addMoviesDetail(
    imageLoader: ImageLoader,
    width: Int,
) {
    composable(
        route = Screen.MoviesDetail.route + "/{movieId}",
        arguments = Screen.MoviesDetail.arguments,
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
        MoviesDetails(
            imageLoader = imageLoader
        )
    }
}