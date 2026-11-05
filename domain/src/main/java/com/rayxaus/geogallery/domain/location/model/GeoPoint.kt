package com.rayxaus.geogallery.domain.location.model

/** [latitude] and [longitude] are expressed in decimal degrees (58.3876) */
data class GeoPoint(
    val latitude: Double,
    val longitude: Double,
    /**
     * estimated horizontal accuracy radius in meters
     */
    val accuracyMeters: Float?
)
