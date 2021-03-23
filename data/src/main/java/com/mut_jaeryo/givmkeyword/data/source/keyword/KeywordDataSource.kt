package com.mut_jaeryo.givmkeyword.data.source.keyword

import com.mut_jaeryo.givmkeyword.data.dto.KeywordModel

interface KeywordDataSource {
    suspend fun getKeyword() : KeywordModel

    suspend fun requestNewKeyword() : KeywordModel
}