package com.moviedetails.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun MovieCover(
    modifier: Modifier = Modifier,
    coverUrl: String?,
    imageLoader: ImageLoader
) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(coverUrl ?: "")
            .build(), imageLoader = imageLoader
    )
    Image(
        modifier = modifier
            .fillMaxWidth()
            .height(screenHeight/5),
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
    )
}