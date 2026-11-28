package com.rayxaus.geogallery.ui.photo.camera

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayxaus.geogallery.domain.photo.service.PhotoSource
import com.rayxaus.geogallery.domain.photo.usecase.CapturePhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CameraViewModel @Inject constructor(private val captureUseCase: CapturePhoto) :
    ViewModel() {
    val uiState = mutableStateOf<CameraUiState>(CameraUiState.Idle)

    fun onShutterClick(photoSource: PhotoSource) {
        if (uiState.value == CameraUiState.Working) {
            return
        }

        uiState.value = CameraUiState.Working
        viewModelScope.launch {
            try {
                captureUseCase(photoSource)
                uiState.value = CameraUiState.Idle
            } catch (e: Exception) {
                // coroutine is canceled. this isnt crash the app, we can throw it further.
                if (e is CancellationException) throw e

                uiState.value = CameraUiState.Error
                Log.e("Capture", "Could not save photo", e)
            }
        }
    }

    fun onErrorDismiss() {
        if (uiState.value == CameraUiState.Error) {
            uiState.value = CameraUiState.Idle
        }
    }
}