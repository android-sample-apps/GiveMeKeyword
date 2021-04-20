package com.mut_jaeryo.givmkeyword.di

import com.mut_jaeryo.data.source.drawing.DrawingDataSource
import com.mut_jaeryo.data.source.drawing.DrawingRepositoryImpl
import com.mut_jaeryo.data.source.drawing.local.LocalDrawingDataSourceImpl
import com.mut_jaeryo.data.source.drawing.remote.RemoteDrawingDataSourceImpl
import com.mut_jaeryo.data.source.user.UserDataSource
import com.mut_jaeryo.data.source.user.UserRepositoryImpl
import com.mut_jaeryo.domain.repositories.DrawingRepository
import com.mut_jaeryo.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideUserRepository(
            @UserModule.LocalLabel localUserDataSource: UserDataSource,
            @UserModule.RemoteLabel remoteUserDataSource: UserDataSource
    ): UserRepository {
        return UserRepositoryImpl(localUserDataSource, remoteUserDataSource)
    }

    @Provides
    fun provideDrawingRepository(
           @DrawingModule.LocalLabel localDrawingDataSourceImpl: DrawingDataSource,
           @DrawingModule.RemoteLabel remoteDrawingDataSourceImpl: DrawingDataSource
    ): DrawingRepository {
        return DrawingRepositoryImpl(localDrawingDataSourceImpl, remoteDrawingDataSourceImpl)
    }
}