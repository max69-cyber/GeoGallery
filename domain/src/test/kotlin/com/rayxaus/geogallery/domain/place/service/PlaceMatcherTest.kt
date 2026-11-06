package com.rayxaus.geogallery.domain.place.service

import com.rayxaus.geogallery.domain.location.model.GeoPoint
import com.rayxaus.geogallery.domain.place.model.Place
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.Instant

class PlaceMatcherTest {
    private fun place(
        id: String = "test-id",
        latitude: Double = 0.0,
        longitude: Double = 0.0,
        radiusMeters: Float = 40f,
    ) = Place(
        id = id,
        centerPoint = GeoPoint(latitude, longitude, null),
        radiusMeters = radiusMeters,
        createdAt = Instant.EPOCH,
        updatedAt = Instant.EPOCH,
    )

    private val testPoint = GeoPoint(0.0, 0.0, null)
    private val placeMatcher = PlaceMatcher()

    @Test
    fun `getPlaceByGeoPoint returns null if there's no places`() {
        val emptyPlaces = emptyList<Place>()
        val result = placeMatcher.getPlaceByGeoPoint(testPoint, emptyPlaces)
        assertNull(result)
    }

    @Test
    fun `getPlaceByGeoPoint returns null if there's no matching places`() {
        val point = GeoPoint(60.0, 30.0, null)
        val outsidePlaces = listOf(place(
            id = "outside",
            latitude = 60.0,
            longitude = 30.001,
            radiusMeters = 40f
        )) // ~55.6 m
        val result = placeMatcher.getPlaceByGeoPoint(point, outsidePlaces)
        assertNull(result)
    }

    @Test
    fun `getPlaceByGeoPoint returns the place if it covers the point's coordinates`() {
        val insidePlace = place(id = "inside", latitude = 0.0001, radiusMeters = 40f) // ~11.1 m
        val result = placeMatcher.getPlaceByGeoPoint(testPoint, listOf(insidePlace))
        assertEquals(insidePlace, result)
    }

    @Test
    fun `getPlaceByGeoPoint returns the place if the distance equals the radius`() {
        val boundaryPlace = place(id = "boundary", latitude = 0.0, radiusMeters = 0f)
        val result = placeMatcher.getPlaceByGeoPoint(testPoint, listOf(boundaryPlace))
        assertEquals(boundaryPlace, result)
    }

    @Test
    fun `getPlaceByGeoPoint returns the place if it covers the point's coordinates and isn't the nearest`() {
        val correctPlace = place(id = "wide-enough", latitude = 0.0003, radiusMeters = 40f) // ~33.4 m
        val differentRadiiPlaces = listOf(
            place(id = "too-small", latitude = 0.0001, radiusMeters = 5f), // ~11.1 m
            correctPlace
        )

        val result = placeMatcher.getPlaceByGeoPoint(testPoint, differentRadiiPlaces)
        assertEquals(correctPlace, result)
    }

    // точка выберет место с ближайшим центром (в пересечении двух мест)
    @Test
    fun `getPlaceByGeoPoint returns the nearest place if a point is on intersection of places`() {
        val correctPlace = place(id = "nearer", latitude = 0.0001, radiusMeters = 40f) // ~11.1 m
        val overlapPlaces = listOf(
            place(id = "farther", latitude = 0.0003, radiusMeters = 40f), // ~33.4 m
            correctPlace
        )

        val result = placeMatcher.getPlaceByGeoPoint(testPoint, overlapPlaces)
        assertEquals(correctPlace, result)
    }
}
