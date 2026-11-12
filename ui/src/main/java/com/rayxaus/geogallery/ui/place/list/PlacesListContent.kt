package com.rayxaus.geogallery.ui.place.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.rayxaus.geogallery.ui.R
import com.rayxaus.geogallery.ui.components.DotRingLoadingIndicator
import com.rayxaus.geogallery.ui.components.GeoGalleryBottomBar
import com.rayxaus.geogallery.ui.navigation.MainTab
import com.rayxaus.geogallery.ui.place.getPlaceDisplayName
import com.rayxaus.geogallery.ui.theme.GeoGalleryTheme
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlacesListContent(
    uiState: PlacesListUiState,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val layoutDirection = LocalLayoutDirection.current
    val activeTab = remember { mutableStateOf(MainTab.Places) }

    val locale = LocalConfiguration.current.locales[0]
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "places",
                        style = MaterialTheme.typography.headlineLarge,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = Color.Transparent,
                ),
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            GeoGalleryBottomBar(
                activeTab = activeTab.value,
                onTabSelected = { newValue -> activeTab.value = newValue },
                onCameraClick = {},
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        when (uiState) {
            PlacesListUiState.Error -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "error",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = "couldn't load places",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            }

            PlacesListUiState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        DotRingLoadingIndicator()
                        Text(
                            text = "loading..",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            }

            is PlacesListUiState.Success -> {
                when (uiState.places.size) {
                    0 -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_empty_places),
                                    contentDescription = "No places",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(72.dp)
                                )
                                Text(
                                    text = "no places yet",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            }
                        }
                    }

                    else -> {
                        LazyColumn(
                            contentPadding = PaddingValues(
                                top = innerPadding.calculateTopPadding(),
                                start = innerPadding.calculateStartPadding(layoutDirection) + 16.dp,
                                end = innerPadding.calculateEndPadding(layoutDirection) + 16.dp,
                                bottom = innerPadding.calculateBottomPadding()
                            ),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                Text(
                                    text = "${uiState.places.size} places in archive",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            items(
                                items = uiState.places,
                                key = { summary -> summary.place.id }) { summary ->
                                OutlinedCard(
                                    onClick = {},
                                    colors = CardDefaults.outlinedCardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                                    ),
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.outlineVariant
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(14.dp)
                                    ) {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .size(100.dp)
                                                .clip(RoundedCornerShape(14.dp))
                                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                        ) {
                                            val coverPath = summary.coverPath
                                            if (coverPath != null) {
                                                AsyncImage(
                                                    model = File(context.filesDir, coverPath),
                                                    contentScale = ContentScale.Crop,
                                                    contentDescription = null,
                                                    modifier = Modifier.matchParentSize(),
                                                )
                                            } else {
                                                Icon(
                                                    painter = painterResource(R.drawable.ic_no_image),
                                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                                    contentDescription = "No image"
                                                )
                                            }
                                        }
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(4.dp),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = getPlaceDisplayName(summary.place, locale),
                                                style = MaterialTheme.typography.titleMedium,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Text(
                                                text = "${summary.photoCount} photos",
                                                style = MaterialTheme.typography.bodySmall,
                                                maxLines = 1,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        Icon(
                                            painter = painterResource(R.drawable.ic_chevron_right),
                                            contentDescription = "To place",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PlacesListContentSuccessPreview() {
    GeoGalleryTheme {
        PlacesListContent(
            uiState = PlacesListUiState.Success(manyPlacesMock),
        )
    }
}

@PreviewLightDark
@Composable
private fun PlacesListContentSuccessEmptyListPreview() {
    GeoGalleryTheme {
        PlacesListContent(
            uiState = PlacesListUiState.Success(emptyList()),
        )
    }
}

@PreviewLightDark
@Composable
private fun PlacesListContentErrorPreview() {
    GeoGalleryTheme {
        PlacesListContent(
            uiState = PlacesListUiState.Error,
        )
    }
}

@PreviewLightDark
@Composable
private fun PlacesListContentLoadingPreview() {
    GeoGalleryTheme {
        PlacesListContent(
            uiState = PlacesListUiState.Loading,
        )
    }
}
