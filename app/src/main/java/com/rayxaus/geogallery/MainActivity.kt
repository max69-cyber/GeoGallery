package com.rayxaus.geogallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rayxaus.geogallery.ui.places.PlacesScreen
import com.rayxaus.geogallery.ui.theme.GeoGalleryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoGalleryTheme {
                PlacesScreen()
            }
        }
    }
}