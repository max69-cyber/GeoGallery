package com.rayxaus.geogallery.ui.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner

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
    var flashState by rememberSaveable { mutableStateOf(FlashState.Off) }
    var isGridVisible by rememberSaveable { mutableStateOf(true) }

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
        flashState = flashState,
        isGridVisible = isGridVisible,
        onPermissionRequest = { launcher.launch(Manifest.permission.CAMERA) },
        onOpenSettings = { openSettings(activity) },
        onFlashChange = { flashState = getNextFlashState(flashState) },
        onGridToggle = { isGridVisible = !isGridVisible },
        onBack = onBack,
        cameraPreview = { modifier ->
            CameraPreview(modifier)
        },
        modifier = modifier,
    )
}

@Composable
private fun CameraPreview(modifier: Modifier = Modifier) {
    val applicationContext = LocalContext.current.applicationContext
    val lifecycleOwner = LocalLifecycleOwner.current

    var provider by remember { mutableStateOf<ProcessCameraProvider?>(null) }
    val preview = remember { Preview.Builder().build() }

    LaunchedEffect(Unit) {
        provider = ProcessCameraProvider.awaitInstance(applicationContext)
    }

    provider?.let { readyProvider ->
        DisposableEffect(readyProvider, lifecycleOwner, preview) {
            readyProvider.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview
            )

            onDispose { readyProvider.unbind(preview) }
        }
    }


    AndroidView(
        factory = { context ->
            val previewView = PreviewView(context)
            
            previewView.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            preview.surfaceProvider = previewView.surfaceProvider

            return@AndroidView previewView
        },
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

private fun getNextFlashState(current: FlashState): FlashState {
    val entries = FlashState.entries
    val next = (current.ordinal + 1) % entries.size

    return entries[next]
}