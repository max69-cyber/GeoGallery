package com.rayxaus.geogallery

import com.rayxaus.geogallery.data.place.local.PlaceDao
import com.rayxaus.geogallery.data.place.local.PlaceEntity
import com.rayxaus.geogallery.domain.common.model.SyncStatus
import java.time.Instant

internal class DebugPlacesSeeder(val placeDao: PlaceDao) {
    private val places = listOf(
        PlaceEntity(
            id = "debug-riverside-park",
            centerLatitude = 56.84,
            centerLongitude = 60.60,
            radiusMeters = 40f,
            name = "Riverside Park",
            createdAt = Instant.parse("2026-11-08T10:00:00Z").toEpochMilli(),
            updatedAt = Instant.parse("2026-11-08T10:00:00Z").toEpochMilli(),
            deletedAt = null,
            syncStatus = SyncStatus.PENDING,
        ),
        PlaceEntity(
            id = "debug-unnamed-place",
            centerLatitude = 56.79,
            centerLongitude = 60.61,
            radiusMeters = 40f,
            name = null,
            createdAt = Instant.parse("2026-11-10T10:00:00Z").toEpochMilli(),
            updatedAt = Instant.parse("2026-11-10T10:00:00Z").toEpochMilli(),
            deletedAt = null,
            syncStatus = SyncStatus.PENDING,
        ),
        PlaceEntity(
            id = "debug-botanical-garden",
            centerLatitude = 56.797,
            centerLongitude = 60.603,
            radiusMeters = 40f,
            name = "Botanical Garden",
            createdAt = Instant.parse("2026-11-07T10:00:00Z").toEpochMilli(),
            updatedAt = Instant.parse("2026-11-07T10:00:00Z").toEpochMilli(),
            deletedAt = null,
            syncStatus = SyncStatus.PENDING,
        ),
        PlaceEntity(
            id = "debug-old-railway-bridge",
            centerLatitude = 56.851,
            centerLongitude = 60.592,
            radiusMeters = 40f,
            name = "Old Railway Bridge",
            createdAt = Instant.parse("2026-11-06T10:00:00Z").toEpochMilli(),
            updatedAt = Instant.parse("2026-11-06T10:00:00Z").toEpochMilli(),
            deletedAt = null,
            syncStatus = SyncStatus.PENDING,
        ),
        PlaceEntity(
            id = "debug-central-market",
            centerLatitude = 56.833,
            centerLongitude = 60.612,
            radiusMeters = 40f,
            name = "Central Market",
            createdAt = Instant.parse("2026-11-05T10:00:00Z").toEpochMilli(),
            updatedAt = Instant.parse("2026-11-05T10:00:00Z").toEpochMilli(),
            deletedAt = null,
            syncStatus = SyncStatus.PENDING,
        ),
        PlaceEntity(
            id = "debug-museum-courtyard",
            centerLatitude = 56.840,
            centerLongitude = 60.616,
            radiusMeters = 40f,
            name = "Museum Courtyard",
            createdAt = Instant.parse("2026-11-04T10:00:00Z").toEpochMilli(),
            updatedAt = Instant.parse("2026-11-04T10:00:00Z").toEpochMilli(),
            deletedAt = null,
            syncStatus = SyncStatus.PENDING,
        ),
        PlaceEntity(
            id = "debug-north-hill",
            centerLatitude = 56.879,
            centerLongitude = 60.610,
            radiusMeters = 40f,
            name = "Observation Deck on the North Hill",
            createdAt = Instant.parse("2026-11-03T10:00:00Z").toEpochMilli(),
            updatedAt = Instant.parse("2026-11-03T10:00:00Z").toEpochMilli(),
            deletedAt = null,
            syncStatus = SyncStatus.PENDING,
        ),
    )

    suspend fun seed() {
        places.forEach {
            placeDao.insert(it)
        }
    }
}
