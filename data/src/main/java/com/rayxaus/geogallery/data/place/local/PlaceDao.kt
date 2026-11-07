package com.rayxaus.geogallery.data.place.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Query("""
        SELECT 
            p.*, 
            (
                SELECT COUNT(*)
                FROM photos
                WHERE place_id = p.id AND deleted_at IS NULL
            ) AS photoCount,
            (
                SELECT local_path 
                FROM photos 
                WHERE place_id = p.id AND deleted_at IS NULL 
                ORDER BY created_at DESC, id 
                LIMIT 1
            ) AS coverPath 
        FROM places AS p 
        WHERE p.deleted_at IS NULL 
        ORDER BY p.updated_at DESC, p.id
    """)
    fun observeAll(): Flow<List<PlaceSummaryRow>>

    @Insert
    suspend fun insert(place: PlaceEntity)
}