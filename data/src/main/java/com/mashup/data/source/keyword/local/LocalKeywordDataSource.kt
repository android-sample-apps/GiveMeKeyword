package com.mashup.data.source.keyword.local

import android.content.Context
import com.mashup.data.db.KeywordDataBase
import com.mashup.data.db.MyPreferences
import com.mashup.data.dto.KeywordModel
import com.mashup.data.source.keyword.KeywordDataSource
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class LocalKeywordDataSource @Inject constructor(
        @ActivityContext private val context: Context,
        private val keywordModel: KeywordDataBase
) : KeywordDataSource {
    override suspend fun getKeyword(): KeywordModel =
            KeywordModel((MyPreferences.getKeyword(context)))

    override suspend fun requestNewKeyword(): KeywordModel {
        val newKeyword = keywordModel.getKeyword(context).apply {
            MyPreferences.setKeyword(context, this)
        }
        return KeywordModel(newKeyword)
    }
}