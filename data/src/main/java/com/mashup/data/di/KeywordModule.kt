package com.mashup.data.di

import com.mashup.data.api.keyword.KeywordService
import com.mashup.data.api.keyword.LocalKeywordServiceImpl
import com.mashup.data.source.drawing.remote.RemoteDrawingDataSourceImpl
import com.mashup.data.source.keyword.KeywordDataSource
import com.mashup.data.source.keyword.KeywordRepositoryImpl
import com.mashup.domain.repositories.KeywordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class KeywordModule {

    @Binds
    abstract fun bindKeywordService(
            localKeywordServiceImpl: LocalKeywordServiceImpl
    ): KeywordService

    @Binds
    abstract fun bindKeywordDataSource(
            remoteKeywordDataSourceImpl: RemoteDrawingDataSourceImpl
    ): KeywordDataSource

    @Binds
    abstract fun bindKeywordRepository(
            keywordRepositoryImpl: KeywordRepositoryImpl
    ): KeywordRepository
}