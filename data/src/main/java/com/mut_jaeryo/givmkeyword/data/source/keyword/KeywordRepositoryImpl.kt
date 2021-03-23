package com.mut_jaeryo.givmkeyword.data.source.keyword

import com.mut_jaeryo.givmkeyword.data.mapper.toDomain
import com.mut_jaeryo.givmkeyword.domain.entities.Keyword
import com.mut_jaeryo.givmkeyword.domain.repositories.KeywordRepository
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
        private val keywordDataSource: KeywordDataSource
) : KeywordRepository {
    override suspend fun getKeyword() : Keyword =
            keywordDataSource.getKeyword().toDomain()

    override suspend fun requestNewKeyword() : Keyword =
            keywordDataSource.requestNewKeyword().toDomain()
}