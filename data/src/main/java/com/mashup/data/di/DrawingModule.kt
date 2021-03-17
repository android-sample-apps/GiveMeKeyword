package com.mashup.data.di

import com.mashup.data.api.drawing.DrawingService
import com.mashup.data.api.drawing.FirebaseDrawingServiceImpl
import com.mashup.data.source.drawing.DrawingDataSource
import com.mashup.data.source.drawing.DrawingRepositoryImpl
import com.mashup.data.source.drawing.remote.RemoteDrawingDataSourceImpl
import com.mashup.domain.repositories.DrawingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
abstract class DrawingModule {

    @Singleton
    @Binds
    abstract fun bindDrawingService(
            firebaseDrawingServiceImpl: FirebaseDrawingServiceImpl
    ): DrawingService

    @Singleton
    @Binds
    abstract fun bindDrawingDataSource(
            remoteDrawingDataSourceImpl: RemoteDrawingDataSourceImpl
    ): DrawingDataSource

    @Singleton
    @Binds
    abstract fun bindDrawingRepository(
            drawingRepositoryImpl: DrawingRepositoryImpl
    ): DrawingRepository
}