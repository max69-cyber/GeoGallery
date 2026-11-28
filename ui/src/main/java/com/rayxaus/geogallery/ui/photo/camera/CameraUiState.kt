package com.rayxaus.geogallery.ui.photo.camera

internal sealed interface CameraUiState {
    data object Idle : CameraUiState
    data object Working : CameraUiState
    data object Error : CameraUiState
}