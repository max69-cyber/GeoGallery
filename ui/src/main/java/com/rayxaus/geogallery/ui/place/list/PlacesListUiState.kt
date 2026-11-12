package com.rayxaus.geogallery.ui.place.list

import com.rayxaus.geogallery.domain.place.model.PlaceSummary

internal sealed interface PlacesListUiState {
    data object Loading : PlacesListUiState
    data class Success(val places: List<PlaceSummary>) : PlacesListUiState
    data object Error : PlacesListUiState
}