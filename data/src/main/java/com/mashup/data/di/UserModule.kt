package com.mashup.data.di

import com.mashup.data.source.user.UserDataSource
import com.mashup.data.source.user.local.LocalUserDataSource
import com.mashup.data.source.user.remote.RemoteUserDataSource
import com.mashup.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
abstract class UserModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteLabel

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalLabel

    @Singleton
    @Binds
    @LocalLabel
    abstract fun bindLocalUserDataSource(
            localUserDataSource: LocalUserDataSource
    ): UserDataSource

    @Singleton
    @Binds
    @RemoteLabel
    abstract fun bindRemoteUserDataSource(
            remoteUserDataSource: RemoteUserDataSource
    ): UserDataSource

    @Singleton
    @Provides
    abstract fun provideUserRepository(
        @UserModule.LocalLabel localUserDataSource: UserDataSource,
        @UserModule.RemoteLabel remoteUserDataSource: UserDataSource
    ): UserRepository
}
