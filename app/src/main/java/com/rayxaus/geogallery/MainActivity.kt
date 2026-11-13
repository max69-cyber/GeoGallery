package com.rayxaus.geogallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rayxaus.geogallery.data.photo.local.PhotoDao
import com.rayxaus.geogallery.data.place.local.PlaceDao
import com.rayxaus.geogallery.ui.place.list.PlacesListScreen
import com.rayxaus.geogallery.ui.theme.GeoGalleryTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var placeDao: PlaceDao

    @Inject
    lateinit var photoDao: PhotoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoGalleryTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    PlacesListScreen()
                    DebugSeedButton(
                        placeDao = placeDao,
                        photoDao = photoDao,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .statusBarsPadding()
                            .padding(16.dp)
                    )
                }

            }
        }
    }
}