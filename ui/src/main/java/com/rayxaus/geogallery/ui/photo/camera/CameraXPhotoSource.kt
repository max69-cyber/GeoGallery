package com.rayxaus.geogallery.ui.photo.camera

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import com.rayxaus.geogallery.domain.photo.service.PhotoSource
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class CameraXPhotoSource(
    private val imageCapture: ImageCapture,
    private val parentDirectory: File,
    private val executor: Executor,
    private val onCapture: () -> Unit,
) : PhotoSource {
    override suspend fun acquire(photoId: String): String {
        val file = File(parentDirectory, "capture-${photoId}.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        // allows to wait for callbacks ends with resume, and only after that execute next lines
        return suspendCancellableCoroutine<String> { continuation ->
            imageCapture.takePicture(
                outputOptions,
                executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        continuation.resume(file.name)
                    }

                    override fun onCaptureStarted() {
                        super.onCaptureStarted()
                        onCapture()
                    }

                    override fun onError(exception: ImageCaptureException) {
                        continuation.resumeWithException(exception)
                    }
                }
            )
        }
    }
}