package com.rayxaus.geogallery.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rayxaus.geogallery.ui.camera.CameraScreen
import com.rayxaus.geogallery.ui.navigation.CameraRoute
import com.rayxaus.geogallery.ui.navigation.PlacesRoute
import com.rayxaus.geogallery.ui.place.list.PlacesListScreen

@Composable
fun GeoGalleryApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = PlacesRoute,
    ) {
        composable<PlacesRoute> {
            PlacesListScreen(
                onCameraClick = { navController.navigate(CameraRoute) }
            )
        }
        composable<CameraRoute> {
            CameraScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}