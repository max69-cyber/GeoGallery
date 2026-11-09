package com.rayxaus.geogallery.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.rayxaus.geogallery.ui.R
import com.rayxaus.geogallery.ui.navigation.MainTab
import com.rayxaus.geogallery.ui.theme.GeoGalleryTheme

@Composable
internal fun GeoGalleryBottomBar(
    activeTab: MainTab,
    onTabSelected: (tab: MainTab) -> Unit,
    onCameraClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .navigationBarsPadding()
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(32.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(vertical = 4.dp, horizontal = 4.dp)
        ) {
            val tabWidth = maxWidth / 3
            val tabAnimationState = animateDpAsState(
                targetValue = tabWidth * activeTab.ordinal,
            )

            Box(
                modifier = Modifier
                    .height(56.dp)
                    .width(tabWidth)
                    .offset(x = tabAnimationState.value)
                    .background(
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(32.dp)
                    )
            ) {}

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                GeoGalleryBottomBarButton(
                    onClick = { onTabSelected(MainTab.Map) },
                    isActive = activeTab == MainTab.Map,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_map),
                        contentDescription = "Map"
                    )
                }
                GeoGalleryBottomBarButton(
                    onClick = { onTabSelected(MainTab.Places) },
                    isActive = activeTab == MainTab.Places,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_places),
                        contentDescription = "Places"
                    )
                }
                GeoGalleryBottomBarButton(
                    onClick = { onTabSelected(MainTab.Photos) },
                    isActive = activeTab == MainTab.Photos,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_photos),
                        contentDescription = "Photos"
                    )
                }
            }
        }

        FilledIconButton(
            onClick = onCameraClick,
            modifier = Modifier
                .size(64.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_camera),
                contentDescription = "Take photo",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun GeoGalleryBottomBarButton(
    onClick: () -> Unit,
    isActive: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent,
            contentColor = if (isActive) MaterialTheme.colorScheme.background
            else MaterialTheme.colorScheme.onBackground,
        ),
        modifier = modifier,
    ) {
        content()
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    GeoGalleryTheme {
        GeoGalleryBottomBar(
            activeTab = MainTab.Places,
            onTabSelected = {},
            onCameraClick = {},
        )
    }
}
