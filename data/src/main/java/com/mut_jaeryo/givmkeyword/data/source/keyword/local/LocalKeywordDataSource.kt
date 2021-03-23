package com.mut_jaeryo.givmkeyword.data.source.keyword.local

import android.content.Context
import com.mut_jaeryo.givmkeyword.data.db.KeywordDataBase
import com.mut_jaeryo.givmkeyword.data.db.MyPreferences
import com.mut_jaeryo.givmkeyword.data.dto.KeywordModel
import com.mut_jaeryo.givmkeyword.data.source.keyword.KeywordDataSource
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