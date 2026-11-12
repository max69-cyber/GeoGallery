package com.rayxaus.geogallery.ui.fake

import com.rayxaus.geogallery.domain.place.model.PlaceSummary
import com.rayxaus.geogallery.domain.place.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class FakePlaceRepository(
    initialPlaces: List<PlaceSummary> = emptyList(),
    /**
     * error to be thrown after subscription on flow of summaries;
     * if it is provided, observe function will throw error instead of returning flow.
     */
    private val failure: Throwable? = null,
) : PlaceRepository {
    private val _places = MutableStateFlow<List<PlaceSummary>>(initialPlaces)

    override fun observePlaceSummaries(): Flow<List<PlaceSummary>> {
        val error = failure ?: return _places.asStateFlow()
        return flow { throw error }
    }

    /** updates repo's internal list of places summaries */
    fun setPlaces(places: List<PlaceSummary>) {
        _places.value = places
    }
}