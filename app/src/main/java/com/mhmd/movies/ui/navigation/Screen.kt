package com.mhmd.movies.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

sealed class Screen(val route: String, val arguments: List<NamedNavArgument>){

    object MoviesList: Screen(
        route = "moviesList",
        arguments = emptyList()
    )

    object MoviesDetail: Screen(
        route = "moviesDetail",
        arguments = listOf(navArgument("movieId") {
            type = NavType.IntType
        })
    )
}

