package com.rayxaus.geogallery.ui.places

import com.rayxaus.geogallery.domain.place.model.PlaceSummary

internal sealed interface PlacesUiState {
    data object Loading : PlacesUiState
    data class Success(val places: List<PlaceSummary>) : PlacesUiState
    data object Error : PlacesUiState
}