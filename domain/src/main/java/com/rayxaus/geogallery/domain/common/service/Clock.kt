package com.rayxaus.geogallery.domain.common.service

import java.time.Instant

interface Clock {
    fun getCurrentTime(): Instant
}