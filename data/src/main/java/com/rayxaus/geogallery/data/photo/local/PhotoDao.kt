package com.rayxaus.geogallery.data.photo.local

import androidx.room.Insert
import androidx.room.Query

interface PhotoDao {
    @Query("SELECT * FROM photos WHERE id = :id")
    suspend fun getById(id: String): PhotoEntity?

    @Insert
    suspend fun insert(photo: PhotoEntity)
}