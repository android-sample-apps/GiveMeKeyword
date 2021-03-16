package com.mashup.domain.repositories

import com.mashup.domain.entities.Keyword

interface KeywordRepository {
    suspend fun getKeyword() : Keyword

    suspend fun requestNewKeyword() : Keyword
}