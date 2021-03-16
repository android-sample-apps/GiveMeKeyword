package com.mashup.data.source.keyword

import com.mashup.domain.entities.Keyword

interface KeywordDataSource {
    suspend fun getKeyword() : Keyword

    suspend fun requestNewKeyword() : Keyword
}