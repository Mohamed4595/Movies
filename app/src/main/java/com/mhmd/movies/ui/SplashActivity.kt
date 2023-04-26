package com.mhmd.movies.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.mhmd.movies.R
import com.mhmd.movies.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoviesTheme(isSplashView = true) {
                val listColors =
                    listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(listColors))
                ) {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        val state = remember {
                            MutableTransitionState(false).apply {
                                targetState = true
                            }
                        }
                        AnimatedVisibility(
                            visibleState = state,
                            enter = fadeIn(
                                animationSpec = repeatable(
                                    2, animation = tween(durationMillis = 2000),
                                    repeatMode = RepeatMode.Reverse
                                )
                            ),
                            exit = fadeOut(
                                animationSpec = tween(
                                    durationMillis = 250,
                                    easing = FastOutLinearInEasing
                                )
                            )
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                contentDescription = null,
                                modifier = Modifier.size(150.dp)

                            )
                        }

                    }


                }
            }
        }
        lifecycleScope.launch {
            delay(3900)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}














