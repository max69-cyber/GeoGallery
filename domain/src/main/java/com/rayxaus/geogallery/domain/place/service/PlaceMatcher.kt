package com.rayxaus.geogallery.domain.place.service

import com.rayxaus.geogallery.domain.location.model.GeoPoint
import com.rayxaus.geogallery.domain.place.model.Place
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class PlaceMatcher {
    /** returns nearest [Place] or `null` if there's no matching places */
    fun getPlaceByGeoPoint(point: GeoPoint, places: List<Place>): Place? =
        places.map { place -> Pair(place, distanceMeters(point, place.centerPoint)) }
            .filter { (place, distance) -> place.radiusMeters >= distance }
            .minByOrNull { it.second }
            ?.first

    private fun distanceMeters(point1: GeoPoint, point2: GeoPoint): Double {
        // haversine formula
        val deltaLatitude = abs(point1.latitude - point2.latitude).toRadian()
        val deltaLongitude = abs(point1.longitude - point2.longitude).toRadian()

        val a = sin(deltaLatitude / 2).pow(2) +
                cos(point1.latitude.toRadian()) *
                cos(point2.latitude.toRadian()) *
                sin(deltaLongitude / 2).pow(2)

        val R = 6_371_000
        return 2 * R * asin(sqrt(a))
    }

    private fun Double.toRadian() = this * PI / 180
}