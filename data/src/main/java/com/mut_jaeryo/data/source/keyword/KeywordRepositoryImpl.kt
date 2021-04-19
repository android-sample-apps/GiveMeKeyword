package com.mut_jaeryo.data.source.keyword

import android.content.Context
import com.mut_jaeryo.data.db.MyPreferences
import com.mut_jaeryo.data.mapper.toDomain
import com.mut_jaeryo.domain.entities.Keyword
import com.mut_jaeryo.domain.repositories.KeywordRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
        @ApplicationContext private val context: Context,
        private val keywordDataSource: KeywordDataSource
) : KeywordRepository {
    override suspend fun getKeyword() : Keyword =
            keywordDataSource.getKeyword().toDomain()

    override suspend fun requestNewKeyword() : Keyword {
        val count = MyPreferences.getRequestCount(context)
        MyPreferences.setRequestKeywordCount(context, count + 1)
        return keywordDataSource.requestNewKeyword().toDomain()
    }

    override suspend fun getRequestCount(): Int =
            MyPreferences.getRequestCount(context)
}