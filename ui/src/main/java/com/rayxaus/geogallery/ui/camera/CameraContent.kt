package com.rayxaus.geogallery.ui.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.rayxaus.geogallery.ui.R
import com.rayxaus.geogallery.ui.theme.GeoGalleryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CameraContent(
    permissionState: CameraPermissionState,
    flashState: FlashState,
    isGridVisible: Boolean,
    onPermissionRequest: () -> Unit,
    onOpenSettings: () -> Unit,
    onFlashChange: () -> Unit,
    onGridToggle: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isCameraAvailable = permissionState == CameraPermissionState.Granted

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding(),
    ) {
        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(
                    onClick = onBack,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = "Back",
                    )
                }
            },
            actions = {
                if (isCameraAvailable) {
                    IconButton(
                        onClick = onFlashChange,
                        modifier = Modifier.semantics {
                            stateDescription = when (flashState) {
                                FlashState.Off -> "Flash: Off"
                                FlashState.Auto -> "Flash: Automatic"
                                FlashState.On -> "Flash: On"
                                FlashState.Flashlight -> "Flash: Flashlight"
                            }
                        },
                    ) {
                        Icon(
                            painter = when (flashState) {
                                FlashState.Off -> painterResource(R.drawable.ic_flash_off)
                                FlashState.Auto -> painterResource(R.drawable.ic_flash_auto)
                                FlashState.On -> painterResource(R.drawable.ic_flash_on)
                                FlashState.Flashlight -> painterResource(R.drawable.ic_flashlight)
                            },
                            contentDescription = "Change flash mode",
                        )
                    }

                    Spacer(Modifier.width(6.dp))

                    IconButton(
                        onClick = onGridToggle,
                        modifier = Modifier.semantics {
                            stateDescription =
                                if (isGridVisible) "Grid enabled" else "Grid disabled"
                        }
                    ) {
                        Icon(
                            painter = if (isGridVisible) painterResource(R.drawable.ic_grid_3x3)
                            else painterResource(R.drawable.ic_grid_3x3_off),

                            contentDescription = "Toggle grid",
                        )
                    }
                }
            },
            windowInsets = WindowInsets(0, 0, 0, 0),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                actionIconContentColor = MaterialTheme.colorScheme.onBackground,
            )
        )
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
            flashState = FlashState.On,
            isGridVisible = false,
            onPermissionRequest = {},
            onOpenSettings = {},
            onFlashChange = {},
            onGridToggle = {},
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
            flashState = FlashState.On,
            isGridVisible = true,
            onPermissionRequest = {},
            onOpenSettings = {},
            onFlashChange = {},
            onGridToggle = {},
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
            flashState = FlashState.On,
            isGridVisible = true,
            onPermissionRequest = {},
            onOpenSettings = {},
            onFlashChange = {},
            onGridToggle = {},
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
            flashState = FlashState.On,
            isGridVisible = true,
            onPermissionRequest = {},
            onOpenSettings = {},
            onFlashChange = {},
            onGridToggle = {},
            onBack = {},
        )
    }
}