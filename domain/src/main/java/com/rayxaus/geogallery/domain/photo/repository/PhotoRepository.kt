package com.rayxaus.geogallery.domain.photo.repository

import com.rayxaus.geogallery.domain.photo.model.Photo

interface PhotoRepository {
    suspend fun save(photo: Photo)
}