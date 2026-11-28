package com.rayxaus.geogallery.domain.location.service

import com.rayxaus.geogallery.domain.location.model.GeoPoint

interface LocationProvider {
    suspend fun getCurrentLocation(): GeoPoint?
}