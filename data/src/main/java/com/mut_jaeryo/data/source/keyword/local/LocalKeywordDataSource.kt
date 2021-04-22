package com.mut_jaeryo.data.source.keyword.local

import android.content.Context
import com.mut_jaeryo.data.db.KeywordFactory
import com.mut_jaeryo.data.db.MyPreferences
import com.mut_jaeryo.data.dto.KeywordModel
import com.mut_jaeryo.data.source.keyword.KeywordDataSource
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalKeywordDataSource @Inject constructor(
        @ApplicationContext private val context: Context,
        private val keywordModel: KeywordFactory
) : KeywordDataSource {
    override suspend fun getKeyword(): KeywordModel =
            KeywordModel((MyPreferences.getKeyword(context)))

    override suspend fun requestNewKeyword(): KeywordModel {
        val newKeyword = keywordModel.getKeyword(context).apply {
            MyPreferences.setKeyword(context, this)
        }
        val count = MyPreferences.getRequestCount(context)
        MyPreferences.setRequestKeywordCount(context, count + 1)

        return KeywordModel(newKeyword)
    }

    override suspend fun setRequestCount(count: Int) {
        MyPreferences.setRequestKeywordCount(context, count)
    }
}