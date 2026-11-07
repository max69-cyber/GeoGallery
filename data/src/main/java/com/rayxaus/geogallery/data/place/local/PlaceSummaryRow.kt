package com.rayxaus.geogallery.data.place.local

import androidx.room.Embedded

data class PlaceSummaryRow(
    @Embedded
    val place: PlaceEntity,
    val photoCount: Int,
    val coverPath: String?,
)
