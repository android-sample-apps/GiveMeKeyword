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

@Module
@InstallIn(ActivityComponent::class)
abstract class DrawingModule {

    @Binds
    abstract fun bindDrawingService(
            firebaseDrawingServiceImpl: FirebaseDrawingServiceImpl
    ): DrawingService

    @Binds
    abstract fun bindDrawingDataSource(
            remoteDrawingDataSourceImpl: RemoteDrawingDataSourceImpl
    ): DrawingDataSource

    @Binds
    abstract fun bindDrawingRepository(
            drawingRepositoryImpl: DrawingRepositoryImpl
    ): DrawingRepository
}