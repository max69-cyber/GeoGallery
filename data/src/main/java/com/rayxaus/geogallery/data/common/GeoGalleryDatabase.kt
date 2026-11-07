package com.rayxaus.geogallery.data.common

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rayxaus.geogallery.data.photo.local.PhotoDao
import com.rayxaus.geogallery.data.photo.local.PhotoEntity
import com.rayxaus.geogallery.data.place.local.PlaceDao
import com.rayxaus.geogallery.data.place.local.PlaceEntity

@Database(entities = [PlaceEntity::class, PhotoEntity::class], version = 1)
abstract class GeoGalleryDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
    abstract fun photoDao(): PhotoDao
}