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
    previewPlace("square", "Cathedral Square", 56.845, 60.608, 12, "photos/square.jpg"),
    previewPlace("bridge", "Old Railway Bridge", 56.851, 60.592, 4, "photos/bridge.jpg"),
    previewPlace("hill", "Observation Deck on the North Hill", 56.879, 60.610, 36, "photos/hill.jpg"),
    previewPlace("market", "Central Market", 56.833, 60.612, 7, "photos/market.jpg"),
    previewPlace("courtyard", "Museum Courtyard", 56.840, 60.616, 0, null),
    previewPlace("trail", "Birch Grove Trail", 56.815, 60.562, 3, "photos/trail.jpg"),
    previewPlace("lake", "West Lake", 56.859, 60.521, 42, "photos/lake.jpg"),
    previewPlace("plaza", "Library Plaza", 56.829, 60.619, 1, "photos/plaza.jpg"),
    previewPlace("orchard", "Orchard", 56.801, 60.578, 6, "photos/orchard.jpg"),
    previewPlace("station", "Old Station", 56.864, 60.604, 15, "photos/station.jpg"),
    previewPlace("lookout", "South Lookout", 56.792, 60.631, 0, null),
    previewPlace("promenade", "East Bank Promenade", 56.826, 60.607, 23, "photos/promenade.jpg"),
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
