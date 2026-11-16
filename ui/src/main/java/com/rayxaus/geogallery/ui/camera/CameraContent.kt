package com.rayxaus.geogallery.ui.camera

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun CameraContent(
    permissionState: CameraPermissionState,
    onPermissionRequest: () -> Unit,
    onOpenSettings: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
}