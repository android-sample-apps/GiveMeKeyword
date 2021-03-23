package com.mut_jaeryo.givmkeyword.domain.repositories

import com.mut_jaeryo.givmkeyword.domain.entities.Keyword

interface KeywordRepository {
    suspend fun getKeyword() : Keyword

    suspend fun requestNewKeyword() : Keyword
}