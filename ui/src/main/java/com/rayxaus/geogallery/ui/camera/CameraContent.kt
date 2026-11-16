package com.rayxaus.geogallery.ui.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.rayxaus.geogallery.ui.theme.GeoGalleryTheme

@Composable
internal fun CameraContent(
    permissionState: CameraPermissionState,
    onPermissionRequest: () -> Unit,
    onOpenSettings: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
    ) {
        // top bar with a back, a flash, and a grid buttons
        // camera preview (4:3)
        // bottom bar with 2 layers:
        // 1: current places pill
        // 2: bar with a target place, a shutter button, and a flip btn for camera
    }
}

@PreviewLightDark
@Composable
private fun CameraContentGrantedStatePreview() {
    GeoGalleryTheme {
        CameraContent(
            permissionState = CameraPermissionState.Granted,
            onPermissionRequest = {},
            onOpenSettings = {},
            onBack = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun CameraContentRequestableStatePreview() {
    GeoGalleryTheme {
        CameraContent(
            permissionState = CameraPermissionState.Requestable,
            onPermissionRequest = {},
            onOpenSettings = {},
            onBack = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun CameraContentNeedsRationaleStatePreview() {
    GeoGalleryTheme {
        CameraContent(
            permissionState = CameraPermissionState.NeedsRationale,
            onPermissionRequest = {},
            onOpenSettings = {},
            onBack = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun CameraContentSettingsRequiredStatePreview() {
    GeoGalleryTheme {
        CameraContent(
            permissionState = CameraPermissionState.SettingsRequired,
            onPermissionRequest = {},
            onOpenSettings = {},
            onBack = {},
        )
    }
}