package com.rayxaus.geogallery.ui.photo.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayxaus.geogallery.domain.photo.service.PhotoSource
import com.rayxaus.geogallery.domain.photo.usecase.CapturePhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(private val captureUseCase: CapturePhoto) : ViewModel() {
    internal fun onShutterClick(photoSource: PhotoSource) {
        viewModelScope.launch {
            captureUseCase(photoSource)
        }
    }
}