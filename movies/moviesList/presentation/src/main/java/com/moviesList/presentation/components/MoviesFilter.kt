package com.moviesList.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mhmd.components.Chip
import com.moviesList.domain.MoviesFilter.NOW_PLAYING
import com.moviesList.domain.MoviesFilter.POPULAR
import com.moviesList.domain.MoviesFilter.TOP_RATED
import com.moviesList.domain.MoviesFilter.UPCOMING
import com.moviesList.presentation.ui.MoviesListEvents
import com.moviesList.presentation.ui.MoviesListState

@Composable
fun MoviesFilter(
    state: MoviesListState,
    events: (MoviesListEvents) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 16.dp),
        modifier =Modifier
            .background(MaterialTheme.colors.surface)) {
        item {
            Chip(
                modifier = Modifier.padding(horizontal = 4.dp),
                title = stringResource(id = com.mhmd.components.R.string.popular),
                selectedBackGroundColor = MaterialTheme.colors.primary,
                selectedTextColor = MaterialTheme.colors.onPrimary,
                isSelected = state.selectedMoviesFilter == POPULAR
            ) {
                events(MoviesListEvents.OnSelectMoviesFilter(POPULAR))
            }
        }

        item {
            Chip(
                modifier = Modifier.padding(horizontal = 8.dp),
                title = stringResource(id = com.mhmd.components.R.string.top_rated),
                selectedBackGroundColor = MaterialTheme.colors.primary,
                selectedTextColor = MaterialTheme.colors.onPrimary,
                isSelected = state.selectedMoviesFilter == TOP_RATED
            ) {
                events(MoviesListEvents.OnSelectMoviesFilter(TOP_RATED))
            }
        }

        item {
            Chip(
                modifier = Modifier.padding(horizontal = 8.dp),
                title = stringResource(id = com.mhmd.components.R.string.now_playing),
                selectedBackGroundColor = MaterialTheme.colors.primary,
                selectedTextColor = MaterialTheme.colors.onPrimary,
                isSelected = state.selectedMoviesFilter == NOW_PLAYING
            ) {
                events(MoviesListEvents.OnSelectMoviesFilter(NOW_PLAYING))
            }
        }

        item {
            Chip(
                modifier = Modifier.padding(horizontal = 8.dp),
                title = stringResource(id = com.mhmd.components.R.string.upcoming),
                selectedBackGroundColor = MaterialTheme.colors.primary,
                selectedTextColor = MaterialTheme.colors.onPrimary,
                isSelected = state.selectedMoviesFilter == UPCOMING
            ) {
                events(MoviesListEvents.OnSelectMoviesFilter(UPCOMING))
            }
        }
    }
}