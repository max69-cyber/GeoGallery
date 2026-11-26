package com.rayxaus.geogallery.domain.photo.usecase

import com.rayxaus.geogallery.domain.common.model.SyncStatus
import com.rayxaus.geogallery.domain.common.service.Clock
import com.rayxaus.geogallery.domain.common.service.IdGenerator
import com.rayxaus.geogallery.domain.photo.model.Photo
import com.rayxaus.geogallery.domain.photo.repository.PhotoRepository
import com.rayxaus.geogallery.domain.photo.service.PhotoSource

class CapturePhoto(
    private val repository: PhotoRepository,
    private val clock: Clock,
    private val idGen: IdGenerator,
) {
    suspend operator fun invoke(photoSource: PhotoSource) {
        val id = idGen.generateId()
        val now = clock.getCurrentTime()

        val path = photoSource.acquire(id)
        val photo = Photo(
            id = id,
            localPath = path,
            location = null,
            placeId = null,
            caption = null,
            createdAt = now,
            updatedAt = now,
            deletedAt = null,
            syncStatus = SyncStatus.PENDING,
        )

        repository.save(photo)
    }
}