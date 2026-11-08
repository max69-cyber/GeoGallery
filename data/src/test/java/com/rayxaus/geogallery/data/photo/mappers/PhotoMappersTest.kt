package com.rayxaus.geogallery.data.photo.mappers

import com.rayxaus.geogallery.data.photo.local.PhotoEntity
import com.rayxaus.geogallery.data.photo.mapper.toPhoto
import com.rayxaus.geogallery.data.photo.mapper.toPhotoEntity
import com.rayxaus.geogallery.domain.common.model.SyncStatus
import com.rayxaus.geogallery.domain.location.model.GeoPoint
import com.rayxaus.geogallery.domain.photo.model.Photo
import com.rayxaus.geogallery.domain.photo.model.PhotoCaption
import com.rayxaus.geogallery.domain.photo.model.PhotoCaptionSource
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant

class PhotoMappersTest {
    val photo = Photo(
        id = "test-photo",
        localPath = "root/test-photo.png",
        location = GeoPoint(
            latitude = 50.0,
            longitude = 30.0,
            accuracyMeters = 30f,
        ),
        placeId = "test-place-id",
        caption = PhotoCaption(
            text = "Test caption",
            source = PhotoCaptionSource.AI,
        ),
        createdAt = Instant.ofEpochMilli(10000),
        updatedAt = Instant.ofEpochMilli(20000),
        deletedAt = Instant.ofEpochMilli(30000),
        syncStatus = SyncStatus.SYNCED,
    )
    val photoWithNullFields = photo.copy(
        location = null,
        placeId = null,
        caption = null,
        deletedAt = null,
    )

    val photoEntity = PhotoEntity(
        id = "test-photo",
        localPath = "root/test-photo.png",
        latitude = 50.0,
        longitude = 30.0,
        accuracyMeters = 30f,
        placeId = "test-place-id",
        captionText = "Test caption",
        captionSource = PhotoCaptionSource.AI,
        createdAt = 10000,
        updatedAt = 20000,
        deletedAt = 30000,
        syncStatus = SyncStatus.SYNCED,
    )
    val photoEntityWithNullFields = photoEntity.copy(
        latitude = null,
        longitude = null,
        accuracyMeters = null,
        placeId = null,
        captionText = null,
        captionSource = null,
        deletedAt = null,
    )


    @Test
    fun `toPhoto preserves all fields when converting`() {
        val result = photoEntity.toPhoto()
        assertEquals(photo, result)
    }

    @Test
    fun `toPhotoEntity preserves all fields when converting`() {
        val result = photo.toPhotoEntity()
        assertEquals(photoEntity, result)
    }

    @Test
    fun `toPhoto correctly converts null fields if data is corrupted`() {
        val testEntity = photoEntityWithNullFields.copy(longitude = 30.0, captionText = "Text")
        val result = testEntity.toPhoto()
        assertEquals(photoWithNullFields, result)
    }

    @Test
    fun `toPhotoEntity correctly converts null fields`() {
        val result = photoWithNullFields.toPhotoEntity()
        assertEquals(photoEntityWithNullFields, result)
    }
}