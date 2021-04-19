package com.mut_jaeryo.givmkeyword.di

import com.mut_jaeryo.data.source.user.UserDataSource
import com.mut_jaeryo.data.source.user.UserRepositoryImpl
import com.mut_jaeryo.data.source.user.local.LocalUserDataSource
import com.mut_jaeryo.data.source.user.remote.RemoteUserDataSource
import com.mut_jaeryo.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
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

    @Binds
    @RemoteLabel
    abstract fun bindRemoteUserDataSource(
            remoteUserDataSource: RemoteUserDataSource
    ): UserDataSource

}