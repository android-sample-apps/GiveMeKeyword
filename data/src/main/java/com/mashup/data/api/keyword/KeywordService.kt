package com.mashup.data.api.keyword

import com.mashup.domain.entities.Keyword

interface KeywordService {
    suspend fun getKeyword() : Keyword

    suspend fun requestNewKeyword() : Keyword
}