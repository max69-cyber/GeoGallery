package com.rayxaus.geogallery.data.place.local

import com.rayxaus.geogallery.domain.common.model.SyncStatus
import com.rayxaus.geogallery.domain.location.model.GeoPoint
import com.rayxaus.geogallery.domain.place.model.Place
import com.rayxaus.geogallery.domain.place.model.PlaceSummary
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant

class PlaceMappersTest {
    val place = Place(
        id = "place-id",
        centerPoint = GeoPoint(
            latitude = 50.0,
            longitude = 30.0,
            accuracyMeters = null,
        ),
        radiusMeters = 30f,
        name = "Place name",
        createdAt = Instant.ofEpochMilli(10000),
        updatedAt = Instant.ofEpochMilli(20000),
        deletedAt = Instant.ofEpochMilli(30000),
        syncStatus = SyncStatus.SYNCED,
    )
    val placeEntity = PlaceEntity(
        id = "place-id",
        centerLatitude = 50.0,
        centerLongitude = 30.0,
        radiusMeters = 30f,
        name = "Place name",
        createdAt = 10000,
        updatedAt = 20000,
        deletedAt = 30000,
        syncStatus = SyncStatus.SYNCED,
    )
    val placeSummary = PlaceSummary(
        place = place,
        photoCount = 4,
        coverPath = "root/summary.png",
    )
    val placeSummaryRow = PlaceSummaryRow(
        place = placeEntity,
        photoCount = 4,
        coverPath = "root/summary.png",
    )

    @Test
    fun `toPlace preserves all fields when converting`() {
        val result = placeEntity.toPlace()
        assertEquals(place, result)
    }

    @Test
    fun `toPlaceEntity preserves all fields when converting`() {
        val result = place.toPlaceEntity()
        assertEquals(placeEntity, result)
    }

    @Test
    fun `toPlaceSummary preserves all fields when converting`() {
        val result = placeSummaryRow.toPlaceSummary()
        assertEquals(placeSummary, result)
    }

    @Test
    fun `toPlaceSummary correctly converts null fields`() {
        val rowWithNullFields = placeSummaryRow.copy(
            place = placeEntity.copy(name = null, deletedAt = null),
            photoCount = 0,
            coverPath = null,
        )
        val summaryWithNullFields = placeSummary.copy(
            place = place.copy(name = null, deletedAt = null),
            photoCount = 0,
            coverPath = null,
        )

        val result = rowWithNullFields.toPlaceSummary()
        assertEquals(summaryWithNullFields, result)
    }
}