package com.mut_jaeryo.givmkeyword.di

import com.mut_jaeryo.givmkeyword.data.api.drawing.DrawingService
import com.mut_jaeryo.givmkeyword.data.api.drawing.FirebaseDrawingServiceImpl
import com.mut_jaeryo.givmkeyword.data.source.drawing.DrawingDataSource
import com.mut_jaeryo.givmkeyword.data.source.drawing.DrawingRepositoryImpl
import com.mut_jaeryo.givmkeyword.data.source.drawing.remote.RemoteDrawingDataSourceImpl
import com.mut_jaeryo.givmkeyword.domain.repositories.DrawingRepository
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