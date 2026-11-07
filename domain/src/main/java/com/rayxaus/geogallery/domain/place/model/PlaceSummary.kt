package com.rayxaus.geogallery.domain.place.model

/** read model for [Place] */
data class PlaceSummary(
    val place: Place,
    val photoCount: Int,
    val coverPath: String?,
)
