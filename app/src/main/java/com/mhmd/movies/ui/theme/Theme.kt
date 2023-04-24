package com.mhmd.movies.ui.theme

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.mhmd.components.*

private val LightColorBlue = lightColors(
    primary = PrimaryColorBlue,
    primaryVariant = PrimaryColorVariantBlue,
    onPrimary = Color.White,
    secondary = SecondaryColorBlue,
    onSecondary = Color.White,
    error = ErrorColor,
    onError = OnErrorColor,
    background = LightColorBackground,
    onBackground = LightColorOnBackground,
    surface = LightColorSurface,
    onSurface = LightColorOnSurface
)
private val DarkColorBlue = darkColors(
    primary = PrimaryColorBlue,
    primaryVariant = PrimaryColorVariantBlue,
    onPrimary = Color.White,
    secondary = SecondaryColorBlue,
    onSecondary = Color.White,
    error = ErrorColor,
    onError = OnErrorColor,
    background = DarkColorBackground,
    onBackground = DarkColorOnBackground,
    surface = DarkColorSurface,
    onSurface = DarkColorOnSurface
)

@Composable
fun MoviesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorBlue
    } else {
        LightColorBlue
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    MaterialTheme(
        colors = colors,
        typography = QuickSandTypography,
        shapes = AppShapes,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background)
            ) {
                Column {
                    content()
                }
            }
        }
    )
}