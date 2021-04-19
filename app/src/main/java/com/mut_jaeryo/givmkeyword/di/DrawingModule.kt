package com.mut_jaeryo.givmkeyword.di

import com.mut_jaeryo.data.api.drawing.DrawingService
import com.mut_jaeryo.data.api.drawing.FirebaseDrawingServiceImpl
import com.mut_jaeryo.data.source.drawing.DrawingDataSource
import com.mut_jaeryo.data.source.drawing.DrawingRepositoryImpl
import com.mut_jaeryo.data.source.drawing.remote.RemoteDrawingDataSourceImpl
import com.mut_jaeryo.domain.repositories.DrawingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
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