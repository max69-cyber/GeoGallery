package com.rayxaus.geogallery.ui.places

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PlacesScreen() {
    val viewModel: PlacesViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    PlacesContent(
        uiState = uiState.value,
    )
}