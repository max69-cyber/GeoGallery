package com.rayxaus.geogallery.domain.place.model

import com.rayxaus.geogallery.domain.location.model.GeoPoint
import com.rayxaus.geogallery.domain.common.model.SyncStatus
import java.time.Instant

/**
 * circle with defined center and radius.
 * if a [GeoPoint] is inside the place's circle or directly on the border,
 * it counts as a candidate for this place's point.
 */
data class Place(
    val id: String,
    val centerPoint: GeoPoint,
    val radiusMeters: Float = 40f,
    val name: String? = null,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant? = null,
    val syncStatus: SyncStatus = SyncStatus.PENDING,
)
