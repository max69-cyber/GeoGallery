package com.rayxaus.geogallery.domain.service

import java.time.Instant

interface Clock {
    fun getCurrentTime(): Instant
}