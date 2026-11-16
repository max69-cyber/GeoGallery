package com.rayxaus.geogallery.ui.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect

@Composable
internal fun CameraScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val activity = requireNotNull(LocalActivity.current) { "CameraScreen requires an Activity" }
    val context = LocalContext.current

    val isCameraPermissionGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
    val shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale(
        activity,
        Manifest.permission.CAMERA,
    )

    var cameraPermissionState by remember {
        mutableStateOf(
            value = when {
                isCameraPermissionGranted -> CameraPermissionState.Granted
                shouldShowRequestPermissionRationale -> CameraPermissionState.NeedsRationale
                else -> CameraPermissionState.Requestable
            }
        )
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        val shouldShowRationale =
            shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)

        cameraPermissionState = when {
            isGranted -> CameraPermissionState.Granted
            shouldShowRationale -> CameraPermissionState.NeedsRationale
            else -> CameraPermissionState.SettingsRequired
        }
    }

    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
        val isGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        val shouldShowRationale = shouldShowRequestPermissionRationale(
            activity,
            Manifest.permission.CAMERA,
        )

        cameraPermissionState = when {
            isGranted -> CameraPermissionState.Granted
            shouldShowRationale -> CameraPermissionState.NeedsRationale
            cameraPermissionState == CameraPermissionState.SettingsRequired ->
                CameraPermissionState.SettingsRequired

            else -> CameraPermissionState.Requestable
        }
    }

    CameraContent(
        permissionState = cameraPermissionState,
        onPermissionRequest = { launcher.launch(Manifest.permission.CAMERA) },
        onOpenSettings = { openSettings(activity) },
        onBack = onBack,
        modifier = modifier,
    )
}

private fun openSettings(activity: Activity) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        "package:${activity.packageName}".toUri()
    )
    activity.startActivity(intent)
}