package com.moviedetails.presentation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.mhmd.components.MovieCard
import com.mhmd.components.PaginatedLazyRow
import com.moviesList.domain.Movie

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HorizontalMoviesSection(
    modifier: Modifier = Modifier,
    title: String,
    imageLoader: ImageLoader,
    movies: List<Movie>,
    canLoadMore: Boolean,
    onLoadMore: () -> Unit,
    onMovieClicked: (moveId: Long) -> Unit
) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Column {
        Text(
            modifier = modifier.padding(horizontal = 16.dp),
            text = title,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold
        )
        val listState = rememberLazyListState()
        movies.PaginatedLazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(start = 48.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
            listState = listState,
            onLoadMore = onLoadMore,
            content = { item, _ ->
                MovieCard(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .width(screenWidth / 3)
                        .height(screenHeight / 4),
                    moviePoster = item.posterImage,
                    voteAverage = item.voteAverage,
                    onMovieClick = {
                        onMovieClicked(item.id)
                    },
                    imageLoader = imageLoader
                )
            },
            canLoadMore = canLoadMore
        )

    }
}