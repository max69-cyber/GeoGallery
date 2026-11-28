package com.rayxaus.geogallery.ui.photo.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.view.OrientationEventListener
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.core.UseCaseGroup
import androidx.camera.core.ViewPort
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.doOnLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun CameraScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = hiltViewModel<CameraViewModel>()

    val activity = requireNotNull(LocalActivity.current) { "CameraScreen requires an Activity" }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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

    val imageCapture = remember { ImageCapture.Builder().build() }
    var controlsRotation by remember { mutableFloatStateOf(0f) }
    var capturePulse by remember { mutableIntStateOf(0) }
    var showShutterOverlay by remember { mutableStateOf(false) }
    val photoSource = remember {
        CameraXPhotoSource(
            imageCapture = imageCapture,
            parentDirectory = context.filesDir,
            executor = ContextCompat.getMainExecutor(context),
            onCapture = {
                capturePulse++
            }
        )
    }

    var flashState by rememberSaveable { mutableStateOf(FlashState.Off) }
    var isGridVisible by rememberSaveable { mutableStateOf(false) }

    var isFrontCameraEnabled by rememberSaveable { mutableStateOf(false) }

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

    LaunchedEffect(capturePulse) {
        if (capturePulse != 0) {
            showShutterOverlay = true
            delay(100.milliseconds)
            showShutterOverlay = false
        }
    }

    LaunchedEffect(imageCapture, flashState) {
        imageCapture.flashMode = when (flashState) {
            FlashState.Off -> ImageCapture.FLASH_MODE_OFF
            FlashState.Auto -> ImageCapture.FLASH_MODE_AUTO
            FlashState.On -> ImageCapture.FLASH_MODE_ON
            FlashState.Flashlight -> ImageCapture.FLASH_MODE_OFF
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

    DisposableEffect(context) {
        var lastOrientation = 0f

        val orientationEventListener = object : OrientationEventListener(context) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) return

                val diff = abs(lastOrientation - orientation)
                val shouldRotate = minOf(diff, 360 - diff) >= 55

                if (shouldRotate) {
                    lastOrientation = when (orientation) {
                        in 315..359, in 0..44 -> 0f
                        in 45..134 -> 90f
                        in 135..224 -> 180f
                        in 225..314 -> 270f
                        else -> lastOrientation
                    }
                    controlsRotation = ((360 - lastOrientation) % 360)

                    val rotation = UseCase.snapToSurfaceRotation(orientation)
                    imageCapture.targetRotation = rotation
                }
            }
        }
        orientationEventListener.enable()

        onDispose { orientationEventListener.disable() }
    }

    CameraContent(
        uiState = viewModel.uiState.value,
        onErrorDismiss = viewModel::onErrorDismiss,
        permissionState = cameraPermissionState,
        flashState = flashState,
        isGridVisible = isGridVisible,
        onPermissionRequest = { launcher.launch(Manifest.permission.CAMERA) },
        onOpenSettings = { openSettings(activity) },
        onFlashChange = { flashState = getNextFlashState(flashState) },
        onGridToggle = { isGridVisible = !isGridVisible },
        onBack = onBack,
        onCameraFlip = { isFrontCameraEnabled = !isFrontCameraEnabled },
        onShutterClick = { viewModel.onShutterClick(photoSource) },
        cameraPreview = { modifier ->
            CameraPreview(
                imageCapture = imageCapture,
                isFrontCameraEnabled = isFrontCameraEnabled,
                isTorchRequested = flashState == FlashState.Flashlight,
                modifier = modifier,
            )
        },
        controlsRotation = controlsRotation,
        modifier = modifier,
        showShutterOverlay = showShutterOverlay,
    )
}

@Composable
private fun CameraPreview(
    imageCapture: ImageCapture,
    isFrontCameraEnabled: Boolean,
    isTorchRequested: Boolean,
    modifier: Modifier = Modifier,
) {
    val applicationContext = LocalContext.current.applicationContext
    val lifecycleOwner = LocalLifecycleOwner.current

    var provider by remember { mutableStateOf<ProcessCameraProvider?>(null) }
    var viewPort by remember { mutableStateOf<ViewPort?>(null) }

    val preview = remember { Preview.Builder().build() }
    var camera by remember { mutableStateOf<Camera?>(null) }

    val cameraSelector =
        if (isFrontCameraEnabled) CameraSelector.DEFAULT_FRONT_CAMERA
        else CameraSelector.DEFAULT_BACK_CAMERA

    LaunchedEffect(Unit) {
        provider = ProcessCameraProvider.awaitInstance(applicationContext)
    }

    val activeCamera = camera
    if (activeCamera?.cameraInfo?.hasFlashUnit() == true) {
        LaunchedEffect(activeCamera, isTorchRequested) {
            activeCamera.cameraControl.enableTorch(isTorchRequested)
        }
    }


    val readyProvider = provider
    val readyViewPort = viewPort

    if (readyProvider != null && readyViewPort != null) {
        DisposableEffect(
            readyProvider,
            lifecycleOwner,
            preview,
            cameraSelector,
            imageCapture,
            readyViewPort,
        ) {
            val useCases = UseCaseGroup.Builder()
                .addUseCase(preview)
                .addUseCase(imageCapture)
                .setViewPort(readyViewPort)
                .build()

            camera = readyProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                useCases,
            )

            onDispose { readyProvider.unbind(preview, imageCapture) }
        }
    }

    AndroidView(
        factory = { context ->
            val previewView = PreviewView(context)

            previewView.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            preview.surfaceProvider = previewView.surfaceProvider

            previewView.doOnLayout { viewPort = previewView.viewPort }

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