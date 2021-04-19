package com.mut_jaeryo.givmkeyword.di

import com.mut_jaeryo.data.source.keyword.KeywordDataSource
import com.mut_jaeryo.data.source.keyword.KeywordRepositoryImpl
import com.mut_jaeryo.data.source.keyword.local.LocalKeywordDataSource
import com.mut_jaeryo.domain.repositories.KeywordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class KeywordModule {

    @Binds
    abstract fun bindKeywordDataSource(
            localKeywordDataSource: LocalKeywordDataSource
    ): KeywordDataSource

    @Binds
    abstract fun bindKeywordRepository(
            keywordRepositoryImpl: KeywordRepositoryImpl
    ): KeywordRepository
}