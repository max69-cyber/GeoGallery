package com.rayxaus.geogallery.ui.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayxaus.geogallery.domain.place.model.PlaceSummary
import com.rayxaus.geogallery.domain.place.repository.PlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class PlacesViewModel @Inject constructor(repository: PlaceRepository) : ViewModel() {
    val uiState: StateFlow<PlacesUiState> = repository.observePlaceSummaries()
        .map<List<PlaceSummary>, PlacesUiState> { list ->
            PlacesUiState.Success(places = list)
        }
        .catch { emit(PlacesUiState.Error) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PlacesUiState.Loading
        )
}