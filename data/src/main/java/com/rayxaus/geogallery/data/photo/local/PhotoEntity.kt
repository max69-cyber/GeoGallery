package com.rayxaus.geogallery.data.photo.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.rayxaus.geogallery.data.place.local.PlaceEntity
import com.rayxaus.geogallery.domain.common.model.SyncStatus
import com.rayxaus.geogallery.domain.photo.model.PhotoCaptionSource

@Entity(
    tableName = "photos",
    foreignKeys = [
        ForeignKey(
            entity = PlaceEntity::class,
            parentColumns = ["id"],
            childColumns = ["place_id"],
        ),
    ],
    indices = [Index(value = ["place_id"])],
)
data class PhotoEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "local_path")
    val localPath: String,
    val latitude: Double?,
    val longitude: Double?,
    @ColumnInfo(name = "accuracy_meters")
    val accuracyMeters: Float?,
    @ColumnInfo(name = "place_id")
    val placeId: String?,
    @ColumnInfo(name = "caption_text")
    val captionText: String?,
    @ColumnInfo(name = "caption_source")
    val captionSource: PhotoCaptionSource?,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,
    @ColumnInfo(name = "deleted_at")
    val deletedAt: Long?,
    @ColumnInfo(name = "sync_status")
    val syncStatus: SyncStatus,
)
