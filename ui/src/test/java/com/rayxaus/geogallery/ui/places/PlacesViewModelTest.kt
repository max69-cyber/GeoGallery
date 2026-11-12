package com.rayxaus.geogallery.ui.places

import app.cash.turbine.test
import com.rayxaus.geogallery.domain.location.model.GeoPoint
import com.rayxaus.geogallery.domain.place.model.Place
import com.rayxaus.geogallery.domain.place.model.PlaceSummary
import com.rayxaus.geogallery.ui.fake.FakePlaceRepository
import com.rayxaus.geogallery.ui.test.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.time.Instant

class PlacesViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    private val mainDispatcherRule = MainDispatcherRule(UnconfinedTestDispatcher())

    private val updatedPlaces = listOf(
        PlaceSummary(
            place = Place(
                id = "riverside-park",
                centerPoint = GeoPoint(56.84, 60.60, accuracyMeters = null),
                name = "Riverside Park",
                createdAt = Instant.EPOCH,
                updatedAt = Instant.EPOCH,
            ),
            photoCount = 3,
            coverPath = "photos/riverside.jpg",
        ),
        PlaceSummary(
            place = Place(
                id = "botanical-garden",
                centerPoint = GeoPoint(56.79, 60.61, accuracyMeters = null),
                name = "Botanical Garden",
                createdAt = Instant.EPOCH,
                updatedAt = Instant.EPOCH,
            ),
            photoCount = 0,
            coverPath = null,
        ),
    )

    @Test
    fun `uiState has Loading as initial state`() = runTest {
        val repository = FakePlaceRepository()
        val viewModel = PlacesViewModel(repository)

        assertEquals(PlacesUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `uiState changes to Success and updates places data inside it`() = runTest {
        val repository = FakePlaceRepository()
        val viewModel = PlacesViewModel(repository)

        viewModel.uiState.test {
            awaitItem()

            repository.setPlaces(emptyList())
            assertEquals(PlacesUiState.Success(places = emptyList()), awaitItem())

            repository.setPlaces(updatedPlaces)
            assertEquals(PlacesUiState.Success(updatedPlaces), awaitItem())
        }
    }

    @Test
    fun `uiState changes to Error when Exception is caught`() = runTest {
        val repository = FakePlaceRepository(
            failure = Exception()
        )
        val viewModel = PlacesViewModel(repository)

        viewModel.uiState.test {
            awaitItem()
            assertEquals(PlacesUiState.Error, awaitItem())
        }
    }
}
