package com.rayxaus.geogallery.data.photo.repository

import com.rayxaus.geogallery.data.photo.local.PhotoDao
import com.rayxaus.geogallery.data.photo.mapper.toPhotoEntity
import com.rayxaus.geogallery.domain.photo.model.Photo
import com.rayxaus.geogallery.domain.photo.repository.PhotoRepository
import javax.inject.Inject

class RoomPhotoRepository @Inject constructor(private val photoDao: PhotoDao) : PhotoRepository {
    override suspend fun save(photo: Photo) = photoDao.insert(photo.toPhotoEntity())
}