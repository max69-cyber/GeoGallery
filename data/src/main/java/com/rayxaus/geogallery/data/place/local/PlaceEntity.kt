package com.rayxaus.geogallery.data.place.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rayxaus.geogallery.domain.common.model.SyncStatus

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "center_latitude")
    val centerLatitude: Double,
    @ColumnInfo(name = "center_longitude")
    val centerLongitude: Double,
    @ColumnInfo(name = "radius_meters")
    val radiusMeters: Float,
    val name: String?,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,
    @ColumnInfo(name = "deleted_at")
    val deletedAt: Long?,
    @ColumnInfo(name = "sync_status")
    val syncStatus: SyncStatus,
)
