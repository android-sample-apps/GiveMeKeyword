package com.mut_jaeryo.domain.repositories

import com.mut_jaeryo.domain.entities.Keyword

interface KeywordRepository {
    suspend fun getKeyword() : Keyword

    suspend fun requestNewKeyword() : Keyword

    suspend fun getRequestCount() : Int
}