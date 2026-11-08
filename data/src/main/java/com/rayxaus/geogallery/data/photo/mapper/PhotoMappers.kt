package com.rayxaus.geogallery.data.photo.mapper

import com.rayxaus.geogallery.data.photo.local.PhotoEntity
import com.rayxaus.geogallery.domain.location.model.GeoPoint
import com.rayxaus.geogallery.domain.photo.model.Photo
import com.rayxaus.geogallery.domain.photo.model.PhotoCaption
import java.time.Instant


fun Photo.toPhotoEntity() = PhotoEntity(
    id = this.id,
    localPath = this.localPath,
    latitude = this.location?.latitude,
    longitude = this.location?.longitude,
    accuracyMeters = this.location?.accuracyMeters,
    placeId = this.placeId,
    captionText = this.caption?.text,
    captionSource = this.caption?.source,
    createdAt = this.createdAt.toEpochMilli(),
    updatedAt = this.updatedAt.toEpochMilli(),
    deletedAt = this.deletedAt?.toEpochMilli(),
    syncStatus = this.syncStatus,
)

fun PhotoEntity.toPhoto() = Photo(
    id = this.id,
    localPath = this.localPath,
    location = if (this.latitude != null && this.longitude != null) {
        GeoPoint(
            latitude = this.latitude,
            longitude = this.longitude,
            accuracyMeters = this.accuracyMeters,
        )
    } else null,
    placeId = this.placeId,
    caption = if (this.captionText != null && this.captionSource != null) {
        PhotoCaption(
            text = this.captionText,
            source = this.captionSource,
        )
    } else null,
    createdAt = Instant.ofEpochMilli(this.createdAt),
    updatedAt = Instant.ofEpochMilli(this.updatedAt),
    deletedAt = this.deletedAt?.let { Instant.ofEpochMilli(it) },
    syncStatus = this.syncStatus,
)