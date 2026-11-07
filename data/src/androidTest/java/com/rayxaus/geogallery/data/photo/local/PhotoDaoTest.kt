package com.rayxaus.geogallery.data.photo.local

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rayxaus.geogallery.data.common.GeoGalleryDatabase
import com.rayxaus.geogallery.data.place.local.PlaceDao
import com.rayxaus.geogallery.data.place.local.PlaceEntity
import com.rayxaus.geogallery.domain.common.model.SyncStatus
import com.rayxaus.geogallery.domain.photo.model.PhotoCaptionSource
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoDaoTest {
    lateinit var database: GeoGalleryDatabase
    lateinit var photoDao: PhotoDao
    lateinit var placeDao: PlaceDao

    val testPlaceEntity = PlaceEntity(
        id = "test-place",
        centerLatitude = 50.0,
        centerLongitude = 30.0,
        radiusMeters = 40f,
        name = "Test place",
        createdAt = 100,
        updatedAt = 100,
        deletedAt = null,
        syncStatus = SyncStatus.PENDING
    )


    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context = context,
            klass = GeoGalleryDatabase::class.java,
        ).build()
        photoDao = database.photoDao()
        placeDao = database.placeDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    private fun photo(
        id: String = "test-photo-id",
        localPath: String = "root/test-photo-path.png",
        latitude: Double? = null,
        longitude: Double? = null,
        accuracyMeters: Float? = null,
        placeId: String? = null,
        captionText: String? = null,
        captionSource: PhotoCaptionSource? = null,
        createdAt: Long = 100,
        updatedAt: Long = 100,
        deletedAt: Long? = null,
        syncStatus: SyncStatus = SyncStatus.PENDING,
    ) = PhotoEntity(
        id = id,
        localPath = localPath,
        latitude = latitude,
        longitude = longitude,
        accuracyMeters = accuracyMeters,
        placeId = placeId,
        captionText = captionText,
        captionSource = captionSource,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
        syncStatus = syncStatus,
    )

    @Test
    fun insertAndGet_preservesAllFieldsWhenInsertingAndGettingById() = runTest {
        placeDao.insert(testPlaceEntity)

        val photoWithoutPlace = photo(
            id = "photo-without-place",
        )
        val photoWithPlace = photo(
            id = "photo-with-place",
            localPath = "root/photo-with-place.jpg",
            latitude = 50.0001,
            longitude = 30.0002,
            accuracyMeters = 40f,
            placeId = testPlaceEntity.id,
            captionText = "Test photo",
            captionSource = PhotoCaptionSource.USER,
            createdAt = 300,
            updatedAt = 300,
        )

        photoDao.insert(photoWithoutPlace)
        photoDao.insert(photoWithPlace)

        val resultWithoutPlace = photoDao.getById(photoWithoutPlace.id)
        val resultWithPlace = photoDao.getById(photoWithPlace.id)

        assertEquals(photoWithoutPlace, resultWithoutPlace)
        assertEquals(photoWithPlace, resultWithPlace)
    }

    @Test
    fun insert_rejectsPhotoWithUnknownPlaceId() = runTest {
        val photoWithNotExistingPlace = photo(
            placeId = "not-existing-place",
        )

        try {
            photoDao.insert(photoWithNotExistingPlace)
            fail("Expected foreign key violation")
        } catch (exception: SQLiteConstraintException) {
            // exception type is already checking it
        }

        val result = photoDao.getById(photoWithNotExistingPlace.id)
        assertNull(result)
    }
}