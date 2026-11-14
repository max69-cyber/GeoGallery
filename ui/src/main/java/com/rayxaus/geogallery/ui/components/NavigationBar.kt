package com.rayxaus.geogallery.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.rayxaus.geogallery.ui.R
import com.rayxaus.geogallery.ui.navigation.MainTab
import com.rayxaus.geogallery.ui.theme.GeoGalleryTheme

enum class BottomBarOrientation {
    Horizontal,
    Vertical,
}

private data class TabItem(
    val tab: MainTab,
    val iconRes: Int,
    val label: String,
)

private val tabsConfig = listOf<TabItem>(
    TabItem(
        tab = MainTab.Map,
        iconRes = R.drawable.ic_map,
        label = "Map",
    ),
    TabItem(
        tab = MainTab.Places,
        iconRes = R.drawable.ic_places,
        label = "Places",
    ),
    TabItem(
        tab = MainTab.Photos,
        iconRes = R.drawable.ic_photos,
        label = "Photos",
    ),
)

@Composable
internal fun NavigationBar(
    activeTab: MainTab,
    onTabSelected: (tab: MainTab) -> Unit,
    onCameraClick: () -> Unit,
    orientation: BottomBarOrientation,
    modifier: Modifier = Modifier
) {
    when (orientation) {
        BottomBarOrientation.Horizontal -> {
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
                        NavigationTabs(
                            tabsConfig = tabsConfig,
                            onClick = onTabSelected,
                            activeTab = activeTab,
                            tabModifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                }

                CameraButton(
                    onClick = onCameraClick
                )
            }
        }

        BottomBarOrientation.Vertical -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Top + WindowInsetsSides.End + WindowInsetsSides.Bottom
                        )
                    )
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
                    val tabWidth = maxHeight / 3
                    val tabAnimationState = animateDpAsState(
                        targetValue = tabWidth * activeTab.ordinal,
                    )

                    Box(
                        modifier = Modifier
                            .width(56.dp)
                            .height(tabWidth)
                            .offset(y = tabAnimationState.value)
                            .background(
                                color = MaterialTheme.colorScheme.onBackground,
                                shape = RoundedCornerShape(32.dp)
                            )
                    ) {}

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(56.dp)
                    ) {
                        NavigationTabs(
                            tabsConfig = tabsConfig,
                            onClick = onTabSelected,
                            activeTab = activeTab,
                            tabModifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )
                    }
                }
                CameraButton(
                    onClick = onCameraClick
                )
            }
        }
    }
}

@Composable
private fun NavigationBarButton(
    onClick: () -> Unit,
    isActive: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val contentColor =
        if (isActive) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .selectable(
                selected = isActive,
                onClick = onClick,
                role = Role.Tab,
                indication = null,
                interactionSource = null,
            ),
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            content()
        }

    }
}

@Composable
private fun NavigationTabs(
    tabsConfig: List<TabItem>,
    onClick: (MainTab) -> Unit,
    activeTab: MainTab,
    tabModifier: Modifier,
) {
    tabsConfig.forEach { config ->
        NavigationBarButton(
            onClick = { onClick(config.tab) },
            isActive = activeTab == config.tab,
            modifier = tabModifier,
        ) {
            Icon(
                painter = painterResource(config.iconRes),
                contentDescription = config.label,
            )
        }
    }
}

@Composable
private fun CameraButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilledIconButton(
        onClick = onClick,
        modifier = modifier
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

@PreviewLightDark
@Composable
private fun Preview() {
    GeoGalleryTheme {
        NavigationBar(
            activeTab = MainTab.Places,
            onTabSelected = {},
            onCameraClick = {},
            orientation = BottomBarOrientation.Horizontal
        )
    }
}

@Preview(
    name = "Phone · Landscape",
    device = "spec:width=411dp,height=891dp,orientation=landscape",
    showSystemUi = true,
)
@Composable
private fun PreviewVertical() {
    GeoGalleryTheme {
        NavigationBar(
            activeTab = MainTab.Places,
            onTabSelected = {},
            onCameraClick = {},
            orientation = BottomBarOrientation.Vertical
        )
    }
}
