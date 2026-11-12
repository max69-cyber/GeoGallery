package com.rayxaus.geogallery.ui.place

import android.text.format.DateFormat
import com.rayxaus.geogallery.domain.place.model.Place
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun getPlaceDisplayName(place: Place, locale: Locale): String {
    val name = place.name
    if (name != null) return name

    val datePattern = DateFormat.getBestDateTimePattern(locale, "MMMd")
    val formatter = SimpleDateFormat(datePattern, locale)
    val dateText = formatter.format(Date.from(place.createdAt))

    return "New place · $dateText"
}
