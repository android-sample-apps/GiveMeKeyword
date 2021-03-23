package com.mut_jaeryo.givmkeyword.di

import com.mut_jaeryo.givmkeyword.data.source.keyword.KeywordDataSource
import com.mut_jaeryo.givmkeyword.data.source.keyword.KeywordRepositoryImpl
import com.mut_jaeryo.givmkeyword.data.source.keyword.local.LocalKeywordDataSource
import com.mut_jaeryo.givmkeyword.domain.repositories.KeywordRepository
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