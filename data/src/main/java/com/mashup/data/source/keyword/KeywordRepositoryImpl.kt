package com.mashup.data.source.keyword

import com.mashup.domain.repositories.KeywordRepository
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
        private val keywordDataSource: KeywordDataSource
) : KeywordRepository {
    override suspend fun getKeyword() =
            keywordDataSource.getKeyword()

    override suspend fun requestNewKeyword() =
            keywordDataSource.requestNewKeyword()
}