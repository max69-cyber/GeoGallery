package com.rayxaus.geogallery.ui.components

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.rayxaus.geogallery.ui.theme.GeoGalleryTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal fun DotRingLoadingIndicator(
    modifier: Modifier = Modifier,
) {
    val dotCount = 20

    val transition = rememberInfiniteTransition(label = "Loading ring")
    val phase = transition.animateFloat(
        initialValue = 0f,
        targetValue = dotCount.toFloat(),
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(
                durationMillis = 1500,
                easing = LinearEasing,
            )
        )
    )

    val dotColor = MaterialTheme.colorScheme.onBackground
    val activeDotColor = MaterialTheme.colorScheme.error

    Canvas(
        modifier = modifier.size(64.dp),
    ) {
        val ringRadius = 27.dp.toPx()
        val activeIndex = phase.value.toInt()

        for (i in 0..<dotCount) {
            val angle = i * 2 * PI / dotCount - PI / 2
            val x = center.x + ringRadius * cos(angle).toFloat()
            val y = center.y + ringRadius * sin(angle).toFloat()

            drawCircle(
                color = if (i == activeIndex) activeDotColor else dotColor,
                radius = 3.dp.toPx(),
                center = Offset(x, y),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun DotRingLoadingIndicatorPreview() {
    GeoGalleryTheme {
        DotRingLoadingIndicator()
    }
}
