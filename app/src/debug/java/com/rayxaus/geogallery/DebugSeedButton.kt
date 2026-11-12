package com.rayxaus.geogallery

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.rayxaus.geogallery.data.place.local.PlaceDao
import kotlinx.coroutines.launch

@Composable
fun DebugSeedButton(
    placeDao: PlaceDao,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    // second seed will crash app. this will guard from it at least on one app session.
    val wasClicked = rememberSaveable { mutableStateOf(false) }

    Button(
        onClick = {
            wasClicked.value = true
            val seeder = DebugPlacesSeeder(placeDao)

            coroutineScope.launch {
                seeder.seed()
            }
        },
        enabled = !wasClicked.value,
        modifier = modifier,
    ) {
        Text(
            text = "seed"
        )
    }
}