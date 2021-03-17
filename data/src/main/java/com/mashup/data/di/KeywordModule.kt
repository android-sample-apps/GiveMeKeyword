package com.mashup.data.di

import com.mashup.data.source.keyword.KeywordDataSource
import com.mashup.data.source.keyword.KeywordRepositoryImpl
import com.mashup.data.source.keyword.local.LocalKeywordDataSource
import com.mashup.domain.repositories.KeywordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
abstract class KeywordModule {

    @Binds
    @Singleton
    abstract fun bindKeywordDataSource(
            localKeywordDataSource: LocalKeywordDataSource
    ): KeywordDataSource

    @Binds
    @Singleton
    abstract fun bindKeywordRepository(
            keywordRepositoryImpl: KeywordRepositoryImpl
    ): KeywordRepository
}