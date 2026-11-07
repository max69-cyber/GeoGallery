package com.rayxaus.geogallery.domain.photo.model

import com.rayxaus.geogallery.domain.location.model.GeoPoint
import com.rayxaus.geogallery.domain.common.model.SyncStatus
import java.time.Instant

data class Photo(
    val id: String,
    /** must not include the platform specific part of path; only relative path from storage root */
    val localPath: String,
    val location: GeoPoint?,
    val placeId: String?,
    val caption: PhotoCaption? = null,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant? = null,
    val syncStatus: SyncStatus = SyncStatus.PENDING,
)

data class PhotoCaption(
    val text: String,
    val source: PhotoCaptionSource,
)

enum class PhotoCaptionSource {
    USER,
    AI,
}