package com.mhmd.movies.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.mhmd.constants.NavigationArgumentsConstants

sealed class Screen(val route: String, val arguments: List<NamedNavArgument>) {

    object MoviesList : Screen(
        route = "moviesList",
        arguments = emptyList()
    )

    object MovieDetails : Screen(
        route = "movieDetails",
        arguments = listOf(navArgument(NavigationArgumentsConstants.MOVIE_ID) {
            type = NavType.LongType
        })
    )
}

