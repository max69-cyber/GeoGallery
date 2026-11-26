package com.rayxaus.geogallery.di

import com.rayxaus.geogallery.domain.common.service.Clock
import com.rayxaus.geogallery.domain.common.service.IdGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.Instant
import java.util.UUID
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppServicesModule {
    @Provides
    @Singleton
    fun provideAppClock() = object : Clock {
        override fun getCurrentTime(): Instant = Instant.now()
    }

    @Provides
    @Singleton
    fun provideAppIdGenerator() = object : IdGenerator {
        override fun generateId(): String = UUID.randomUUID().toString()
    }
}