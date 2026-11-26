package com.rayxaus.geogallery.di

import com.rayxaus.geogallery.data.photo.repository.RoomPhotoRepository
import com.rayxaus.geogallery.data.place.repository.RoomPlaceRepository
import com.rayxaus.geogallery.domain.photo.repository.PhotoRepository
import com.rayxaus.geogallery.domain.place.repository.PlaceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPlaceRepository(repository: RoomPlaceRepository): PlaceRepository

    @Binds
    @Singleton
    abstract fun bindPhotoRepository(repository: RoomPhotoRepository): PhotoRepository
}