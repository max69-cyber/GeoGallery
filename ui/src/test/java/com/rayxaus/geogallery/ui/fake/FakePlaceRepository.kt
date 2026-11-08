package com.rayxaus.geogallery.ui.fake

import com.rayxaus.geogallery.domain.place.model.PlaceSummary
import com.rayxaus.geogallery.domain.place.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakePlaceRepository(initialPlaces: List<PlaceSummary>) : PlaceRepository {
    private val _places = MutableStateFlow<List<PlaceSummary>>(initialPlaces)

    override fun observePlaceSummaries(): Flow<List<PlaceSummary>> = _places.asStateFlow()
}