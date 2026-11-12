package com.rayxaus.geogallery.ui.place.list

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PlacesListScreen() {
    val viewModel: PlacesListViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    PlacesListContent(
        uiState = uiState.value,
    )
}