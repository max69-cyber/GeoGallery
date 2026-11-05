package com.rayxaus.geogallery.domain.fake

import com.rayxaus.geogallery.domain.service.IdGenerator

class FakeIdGenerator(vararg ids: String) : IdGenerator {
    private val iterator = ids.iterator()

    override fun generateId(): String {
        check(iterator.hasNext()) {
            "No more ids available"
        }

        return iterator.next()
    }
}