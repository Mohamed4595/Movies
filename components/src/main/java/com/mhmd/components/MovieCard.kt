package com.mhmd.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@ExperimentalAnimationApi
@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    moviePoster: String,
    voteAverage: Double?,
    onMovieClick: () -> Unit,
    imageLoader: ImageLoader
) {

    Card(
        modifier = modifier
            .clickable { onMovieClick() },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary)
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(moviePoster)
                    .build(), imageLoader = imageLoader
            )
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(bottomStart = 2.dp, bottomEnd = 2.dp)),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            if (voteAverage != null)
                AnimatedVisibility(
                    modifier = Modifier
                        .fillMaxWidth(.3f)
                        .align(Alignment.BottomCenter)
                        .clip(
                            RoundedCornerShape(
                                topEnd = 8.dp,
                                topStart = 8.dp,
                                bottomEnd = 0.dp,
                                bottomStart = 0.dp
                            )
                        )
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(
                                topEnd = 8.dp,
                                topStart = 8.dp,
                                bottomEnd = 0.dp,
                                bottomStart = 0.dp
                            )
                        ), visible = true
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = voteAverage.toString(),
                        style = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.onPrimary,
                            fontWeight = FontWeight.Bold,
                            textAlign= TextAlign.Center
                        )
                    )
                }
        }

    }
}
















