package com.rayxaus.geogallery.domain.fake

import com.rayxaus.geogallery.domain.service.Clock
import java.time.Instant

class FakeClock(vararg timestamps: Instant) : Clock {
    private val iterator = timestamps.iterator()

    override fun getCurrentTime(): Instant {
        check(iterator.hasNext()) {
            "No more timestamps available"
        }

        return iterator.next()
    }
}