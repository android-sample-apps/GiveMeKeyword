package com.mashup.data.source.keyword

import com.mashup.data.dto.KeywordModel

interface KeywordDataSource {
    suspend fun getKeyword() : KeywordModel

    suspend fun requestNewKeyword() : KeywordModel
}