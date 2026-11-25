package com.rayxaus.geogallery.domain.photo.service

interface PhotoSource {
    /**
     * captures a photo and saves it as JPEG
     *
     * @param photoId: assigns to photo before capture, name of a photo must contain this id
     * @return: the path to the saved photo, relative to the photo storage root
     */
    suspend fun capture(photoId: String): String
}