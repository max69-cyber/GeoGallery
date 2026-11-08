package com.rayxaus.geogallery.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rayxaus.geogallery.ui.R

val dotMatrix = FontFamily(Font(R.font.dot_matrix))

@OptIn(ExperimentalTextApi::class)
val geologica = FontFamily(
    Font(
        R.font.geologica,
        variationSettings = FontVariation.Settings(FontVariation.weight(500)),
    ),
)
val ibmPlexMono = FontFamily(Font(R.font.ibm_plex_mono_regular))

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = dotMatrix,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
        lineHeight = 48.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = geologica,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = ibmPlexMono,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 14.sp,
    ),
)
