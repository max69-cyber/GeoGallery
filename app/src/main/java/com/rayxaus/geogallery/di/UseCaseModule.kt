package com.rayxaus.geogallery.di

import com.rayxaus.geogallery.domain.common.service.Clock
import com.rayxaus.geogallery.domain.common.service.IdGenerator
import com.rayxaus.geogallery.domain.photo.repository.PhotoRepository
import com.rayxaus.geogallery.domain.photo.usecase.CapturePhoto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideCapturePhoto(
        repository: PhotoRepository,
        clock: Clock,
        idGenerator: IdGenerator,
    ) = CapturePhoto(repository, clock, idGenerator)
}