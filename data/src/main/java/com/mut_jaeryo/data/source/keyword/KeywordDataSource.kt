package com.mut_jaeryo.data.source.keyword

import com.mut_jaeryo.data.dto.KeywordModel

interface KeywordDataSource {
    suspend fun getKeyword() : KeywordModel

    suspend fun requestNewKeyword() : KeywordModel
}