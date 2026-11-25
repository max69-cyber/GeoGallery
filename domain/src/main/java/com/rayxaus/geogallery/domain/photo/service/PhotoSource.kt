package com.rayxaus.geogallery.domain.photo.service

interface PhotoSource {
    /**
     * acquires an image and saves it to the app’s photo storage
     *
     * @param photoId assigns to photo before creation
     * @return the path to the saved photo, relative to the photo storage root
     */
    suspend fun acquire(photoId: String): String
}