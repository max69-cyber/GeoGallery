package com.rayxaus.geogallery.data.place.local

import com.rayxaus.geogallery.domain.location.model.GeoPoint
import com.rayxaus.geogallery.domain.place.model.Place
import com.rayxaus.geogallery.domain.place.model.PlaceSummary
import java.time.Instant

fun Place.toPlaceEntity() = PlaceEntity(
    id = this.id,
    centerLatitude = this.centerPoint.latitude,
    centerLongitude = this.centerPoint.longitude,
    radiusMeters = this.radiusMeters,
    name = this.name,
    createdAt = this.createdAt.toEpochMilli(),
    updatedAt = this.updatedAt.toEpochMilli(),
    deletedAt = this.deletedAt?.toEpochMilli(),
    syncStatus = this.syncStatus,
)

fun PlaceEntity.toPlace() = Place(
    id = this.id,
    centerPoint = GeoPoint(
        latitude = this.centerLatitude,
        longitude = this.centerLongitude,
        accuracyMeters = null,
    ),
    radiusMeters = this.radiusMeters,
    name = this.name,
    createdAt = Instant.ofEpochMilli(this.createdAt),
    updatedAt = Instant.ofEpochMilli(this.updatedAt),
    deletedAt = this.deletedAt?.let { Instant.ofEpochMilli(it) },
    syncStatus = this.syncStatus,
)

fun PlaceSummaryRow.toPlaceSummary() = PlaceSummary(
    place = this.place.toPlace(),
    photoCount = this.photoCount,
    coverPath = this.coverPath,
)