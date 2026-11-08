package com.rayxaus.geogallery.ui.places

import com.rayxaus.geogallery.domain.location.model.GeoPoint
import com.rayxaus.geogallery.domain.place.model.Place
import com.rayxaus.geogallery.domain.place.model.PlaceSummary
import java.time.Instant

internal val manyPlacesMock = listOf(
    previewPlace("pond", "City Pond", 56.841, 60.599, 24, "photos/pond.jpg"),
    previewPlace("dam", "Historic Dam", 56.838, 60.605, 18, "photos/dam.jpg"),
    previewPlace("park", "Riverside Park by the Embankment", 56.844, 60.601, 9, "photos/park.jpg"),
    previewPlace("garden", "Botanical Garden", 56.800, 60.605, 1, "photos/garden.jpg"),
    previewPlace("unnamed", null, 56.836, 60.588, 0, null),
)

private fun previewPlace(
    id: String,
    name: String?,
    latitude: Double,
    longitude: Double,
    photoCount: Int,
    coverPath: String?,
): PlaceSummary {
    val timestamp = Instant.parse("2026-10-19T10:00:00Z")
    return PlaceSummary(
        place = Place(
            id = id,
            centerPoint = GeoPoint(latitude, longitude, accuracyMeters = null),
            name = name,
            createdAt = timestamp,
            updatedAt = timestamp,
        ),
        photoCount = photoCount,
        coverPath = coverPath,
    )
}
