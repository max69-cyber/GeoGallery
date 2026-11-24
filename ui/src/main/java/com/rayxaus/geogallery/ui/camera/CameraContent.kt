package com.rayxaus.geogallery.ui.camera

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.rayxaus.geogallery.domain.place.model.PlaceSummary
import com.rayxaus.geogallery.ui.R
import com.rayxaus.geogallery.ui.theme.GeoGalleryTheme
import com.rayxaus.geogallery.ui.theme.Red

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
    onCameraFlip: () -> Unit,
    onShutterClick: () -> Unit,
    cameraPreview: @Composable (modifier: Modifier) -> Unit,
    controlsRotation: Float,
    showShutterOverlay: Boolean,
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
                        modifier = Modifier.rotate(controlsRotation),
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
                            modifier = Modifier.rotate(controlsRotation),
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
                            modifier = Modifier.rotate(controlsRotation),
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
                            ratio = 3f / 4f
                        ),
                    ) {
                        cameraPreview(Modifier.matchParentSize())

                        if (isGridVisible) {
                            Canvas(modifier = Modifier.matchParentSize()) {
                                val height = size.height
                                val width = size.width

                                val lineOffsets = listOf(
                                    Pair(
                                        Offset(width / 3, 0f),
                                        Offset(width / 3, height),
                                    ),
                                    Pair(
                                        Offset(width / 3 * 2, 0f),
                                        Offset(width / 3 * 2, height),
                                    ),
                                    Pair(
                                        Offset(0f, height / 3),
                                        Offset(width, height / 3),
                                    ),
                                    Pair(
                                        Offset(0f, height / 3 * 2),
                                        Offset(width, height / 3 * 2),
                                    )
                                )

                                lineOffsets.forEach { pair ->
                                    drawLine(
                                        start = pair.first,
                                        end = pair.second,
                                        color = Color.White.copy(alpha = 0.5f),
                                        strokeWidth = 1.dp.toPx()
                                    )
                                }
                            }
                        }

                        if (showShutterOverlay) {
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(MaterialTheme.colorScheme.background)
                            )
                        }
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp, bottom = 48.dp)
                ) {
                    // bottom bar with 2 layers:
                    // 1: current places pill
                    CurrentPlacePill(
                        placeName = "NEW PLACE",
                        accuracy = 10,
                    )
                    // 2: bar with a target place, a shutter button, and a flip btn for camera
                    CameraBottomBar(
                        targetPlaceSummary = null,
                        onTargetPlaceClick = {},
                        onShutterClick = onShutterClick,
                        onFlipClick = onCameraFlip,
                        controlsRotation = controlsRotation,
                    )
                }
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

@Composable
private fun CurrentPlacePill(
    placeName: String,
    accuracy: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = CircleShape
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(Red, CircleShape)
        )
        Text(
            text = placeName,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f, fill = false)
        )
        Text(
            text = "·",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "±$accuracy m",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun CameraBottomBar(
    targetPlaceSummary: PlaceSummary?,
    onTargetPlaceClick: () -> Unit,
    onShutterClick: () -> Unit,
    onFlipClick: () -> Unit,
    controlsRotation: Float,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable(
                        onClick = onTargetPlaceClick,
                        role = Role.Button,
                        enabled = targetPlaceSummary != null,
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_new_location),
                    contentDescription = "Target place",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.rotate(controlsRotation)
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    .semantics {
                        contentDescription = "Take photo"
                    }
                    .clickable(
                        onClick = onShutterClick,
                        role = Role.Button,
                    )
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onBackground)
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    .clickable(
                        onClick = onFlipClick,
                        role = Role.Button,
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_flip_camera),
                    contentDescription = "Flip camera",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.rotate(controlsRotation)
                )
            }
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
            onCameraFlip = {},
            onShutterClick = {},
            cameraPreview = {},
            controlsRotation = 0f,
            showShutterOverlay = false,
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
            onCameraFlip = {},
            onShutterClick = {},
            cameraPreview = {},
            controlsRotation = 0f,
            showShutterOverlay = false,
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
            onCameraFlip = {},
            onShutterClick = {},
            cameraPreview = {},
            controlsRotation = 0f,
            showShutterOverlay = false,
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
            onCameraFlip = {},
            onShutterClick = {},
            cameraPreview = {},
            controlsRotation = 0f,
            showShutterOverlay = false,
        )
    }
}