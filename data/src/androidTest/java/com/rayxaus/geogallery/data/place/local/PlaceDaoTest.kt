package com.rayxaus.geogallery.data.place.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.rayxaus.geogallery.data.common.GeoGalleryDatabase
import com.rayxaus.geogallery.data.photo.local.PhotoDao
import com.rayxaus.geogallery.data.photo.local.PhotoEntity
import com.rayxaus.geogallery.domain.common.model.SyncStatus
import com.rayxaus.geogallery.domain.photo.model.PhotoCaptionSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlaceDaoTest {
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
    fun insert_addsPlaceToListAndPreservesFields() = runTest {
        placeDao.insert(testPlaceEntity)
        val summaryList = listOf(
            PlaceSummaryRow(
                place = testPlaceEntity,
                photoCount = 0,
                coverPath = null
            )
        )

        val result = placeDao.observeAll().first()
        assertEquals(summaryList, result)
    }

    @Test
    fun observeAll_changesPlacesDataWithNewPhotos() = runTest {
        val firstPhoto = photo(
            id = "first-photo",
            localPath = "root/first-photo.jpg",
            placeId = testPlaceEntity.id,
            createdAt = 50000
        )
        val secondPhoto = photo(
            id = "second-test-photo",
            localPath = "root/second-test-photo.jpg",
            placeId = testPlaceEntity.id,
        )
        val deletedPhoto = photo(
            id = "deleted-photo",
            localPath = "root/deleted-photo.jpg",
            placeId = testPlaceEntity.id,
            createdAt = 100000,
            deletedAt = 150000
        )
        val firstSummaryList = listOf(
            PlaceSummaryRow(
                place = testPlaceEntity,
                photoCount = 1,
                coverPath = firstPhoto.localPath
            )
        )
        val secondSummaryList = listOf(
            PlaceSummaryRow(
                place = testPlaceEntity,
                photoCount = 2,
                coverPath = firstPhoto.localPath
            )
        )

        placeDao.insert(testPlaceEntity)

        placeDao.observeAll().test {
            awaitItem()

            photoDao.insert(firstPhoto)
            assertEquals(firstSummaryList, awaitItem())

            photoDao.insert(secondPhoto)
            assertEquals(secondSummaryList, awaitItem())

            photoDao.insert(deletedPhoto)
            assertEquals(secondSummaryList, awaitItem())
        }
    }

    @Test
    fun observeAll_sortingPlacesAndIgnoresDeleted() = runTest {
        val firstPlace = testPlaceEntity.copy(id = "first-place")
        val secondPlace = testPlaceEntity.copy(id = "second-place", updatedAt = 50000)
        val deletedPlace = testPlaceEntity.copy(id = "deleted-place", deletedAt = 1000)
        val sortedList = listOf(
            PlaceSummaryRow(
                place = secondPlace,
                photoCount = 0,
                coverPath = null
            ),
            PlaceSummaryRow(
                place = firstPlace,
                photoCount = 0,
                coverPath = null
            ),
        )

        placeDao.insert(firstPlace)
        placeDao.insert(secondPlace)
        placeDao.insert(deletedPlace)

        val result = placeDao.observeAll().first()
        assertEquals(sortedList, result)
    }
}