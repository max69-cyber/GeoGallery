package com.rayxaus.geogallery.domain.place.repository

import com.rayxaus.geogallery.domain.place.model.PlaceSummary
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    fun observePlaceSummaries(): Flow<List<PlaceSummary>>
}