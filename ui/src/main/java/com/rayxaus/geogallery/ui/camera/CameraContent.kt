package com.rayxaus.geogallery.ui.camera

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
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
    cameraPreview: @Composable (modifier: Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isCameraAvailable = permissionState == CameraPermissionState.Granted
    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT

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
        when (permissionState) {
            CameraPermissionState.Granted -> {
                // camera preview (4:3)
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                ) {
                    Box(
                        modifier = Modifier.aspectRatio(
                            ratio = if (isPortrait) (3f / 4f) else (4f / 3f)
                        ),
                    ) {
                        cameraPreview(Modifier.matchParentSize())
                    }
                }
                // bottom bar with 2 layers:
                // 1: current places pill
                // 2: bar with a target place, a shutter button, and a flip btn for camera
            }

            CameraPermissionState.Requestable -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CameraAccessPrompt(
                        splashRes = R.drawable.ic_camera_splash,
                        title = "allow camera access",
                        description = "app needs camera access to take photos",
                        onButtonClick = onPermissionRequest,
                        buttonText = "allow access",
                    )
                }
            }

            CameraPermissionState.NeedsRationale -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CameraAccessPrompt(
                        splashRes = R.drawable.ic_warn_splash,
                        title = "camera access denied",
                        description = "allow camera access to take photos",
                        onButtonClick = onPermissionRequest,
                        buttonText = "try again",
                    )
                }
            }

            CameraPermissionState.SettingsRequired -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CameraAccessPrompt(
                        splashRes = R.drawable.ic_lock_splash,
                        title = "enable camera access",
                        description = "in app settings, open Permissions and allow Camera access",
                        onButtonClick = onOpenSettings,
                        buttonText = "open settings",
                    )
                }
            }
        }
    }
}

@Composable
private fun CameraAccessPrompt(
    @DrawableRes splashRes: Int,
    title: String,
    description: String,
    onButtonClick: () -> Unit,
    buttonText: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 20.dp),
    ) {
        Icon(
            painter = painterResource(splashRes),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(96.dp),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
        }
        FilledTonalButton(
            onClick = onButtonClick,
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.background
            ),
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }
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
            cameraPreview = {}
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
            cameraPreview = {}
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
            cameraPreview = {}
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
            cameraPreview = {}
        )
    }
}