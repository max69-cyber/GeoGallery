package com.rayxaus.geogallery.di

import android.content.Context
import androidx.room.Room
import com.rayxaus.geogallery.data.common.GeoGalleryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GeoGalleryDatabase =
        Room.databaseBuilder(
            context = context,
            klass = GeoGalleryDatabase::class.java,
            name = "geogallery.db",
        ).build()

    @Provides
    @Singleton
    fun providePlaceDao(database: GeoGalleryDatabase) = database.placeDao()
}