package com.mashup.data.source.keyword

import com.mashup.data.mapper.toDomain
import com.mashup.domain.entities.Keyword
import com.mashup.domain.repositories.KeywordRepository
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
        private val keywordDataSource: KeywordDataSource
) : KeywordRepository {
    override suspend fun getKeyword() : Keyword =
            keywordDataSource.getKeyword().toDomain()

    override suspend fun requestNewKeyword() : Keyword =
            keywordDataSource.requestNewKeyword().toDomain()
}