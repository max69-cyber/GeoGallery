package com.rayxaus.geogallery.ui.camera

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CameraScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilledTonalButton(
        onClick = onBack,
        modifier = Modifier.fillMaxSize()
    ) {

    }
}