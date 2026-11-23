package com.rayxaus.geogallery.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rayxaus.geogallery.ui.camera.CameraScreen
import com.rayxaus.geogallery.ui.navigation.CameraRoute
import com.rayxaus.geogallery.ui.navigation.PlacesRoute
import com.rayxaus.geogallery.ui.place.list.PlacesListScreen

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun GeoGalleryApp() {
    val navController = rememberNavController()

    val activity = LocalActivity.current
    val currentEntry by navController.currentBackStackEntryAsState()

    val entry = currentEntry
    if (activity != null && entry != null) {
        LaunchedEffect(activity, entry) {
            if (entry.destination.hasRoute<CameraRoute>()) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }
    }

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