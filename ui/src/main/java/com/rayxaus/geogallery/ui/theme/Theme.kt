package com.rayxaus.geogallery.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    background = Black,
    onBackground = White,
    surfaceVariant = Gray800,
    surfaceContainerLow = Gray950,
    onSurface = White,
    onSurfaceVariant = WarmGray400,
    outlineVariant = Gray800,
    primary = White,
    onPrimary = Black,
    error = Red,
)

private val LightColorScheme = lightColorScheme(
    background = White,
    onBackground = Black,
    surfaceVariant = WarmGray200,
    surfaceContainerLow = WarmGray100,
    onSurface = Black,
    onSurfaceVariant = WarmGray600,
    outlineVariant = WarmGray200,
    primary = Black,
    onPrimary = White,
    error = Red,
)

@Composable
fun GeoGalleryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}