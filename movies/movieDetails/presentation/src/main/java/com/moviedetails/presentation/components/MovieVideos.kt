package com.moviedetails.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import com.mhmd.components.GrayTransparentColor
import com.mhmd.components.R
import com.moviedetails.domain.Video
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.absoluteValue


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieVideos(
    modifier: Modifier = Modifier,
    videos: List<Video>,
    isChanged: Boolean,
    initializedYouTubePlayer: Map<Int, YouTubePlayer>,
    onVideoChanged: (Boolean) -> Unit,
    onSetVideo: (Int, YouTubePlayer) -> Unit
) {
    Column {
        Text(
            modifier = modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.trailers),
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold
        )
        val pagerState = rememberPagerState()
        LaunchedEffect(pagerState) {
            // Collect from the a snapshotFlow reading the currentPage
            snapshotFlow { pagerState.currentPage }.collectLatest { page ->
                // Do something with each page change, for example:
                // viewModel.sendPageSelectedEvent(page)
                onVideoChanged(false)
            }
        }

        if (initializedYouTubePlayer.isNotEmpty() && initializedYouTubePlayer[pagerState.currentPage] != null) {
            if (!isChanged) {
                initializedYouTubePlayer[pagerState.currentPage]!!.loadVideo(
                    videos[pagerState.currentPage].key,
                    0f
                )
                onVideoChanged(true)
            }
        }

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(start = 44.dp, end = 12.dp, top = 8.dp, bottom = 0.dp),
            pageCount = videos.size,
            state = pagerState
        ) { page ->
            Card(
                Modifier
                    .padding(horizontal = 4.dp)
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                AndroidView(
                    factory = {
                        val view = YouTubePlayerView(it)
                        view.enableAutomaticInitialization = false

                        view.addYouTubePlayerListener(
                            object : AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    if (page == 0)
                                        youTubePlayer.loadVideo(videos[page].key, 0f)
                                    else
                                        youTubePlayer.cueVideo(videos[page].key, 0f)

                                    onSetVideo(page, youTubePlayer)
                                }
                            }
                        )

                        view
                    })

            }
        }

        Row(
            Modifier
                .padding(top = 8.dp, bottom = 16.dp, start = 44.dp, end = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(videos.size) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) MaterialTheme.colors.primary
                    else GrayTransparentColor
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)

                )
            }
        }

    }

}