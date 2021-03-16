package com.mashup.data.source.keyword.local

import com.mashup.data.api.keyword.KeywordService
import com.mashup.data.source.keyword.KeywordDataSource
import javax.inject.Inject

class LocalKeywordDataSource @Inject constructor(
        private val keywordService: KeywordService
) : KeywordDataSource {
    override suspend fun getKeyword() =
            keywordService.getKeyword()

    override suspend fun requestNewKeyword() =
            keywordService.requestNewKeyword()
}