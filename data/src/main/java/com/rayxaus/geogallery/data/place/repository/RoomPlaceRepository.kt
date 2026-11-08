package com.rayxaus.geogallery.data.place.repository

import com.rayxaus.geogallery.data.place.local.PlaceDao
import com.rayxaus.geogallery.data.place.mapper.toPlaceSummary
import com.rayxaus.geogallery.domain.place.model.PlaceSummary
import com.rayxaus.geogallery.domain.place.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomPlaceRepository @Inject constructor(private val placeDao: PlaceDao) : PlaceRepository {
    override fun observePlaceSummaries(): Flow<List<PlaceSummary>> =
        placeDao.observeAll().map { summaryRows ->
            summaryRows.map { summaryRow -> summaryRow.toPlaceSummary() }
        }
}