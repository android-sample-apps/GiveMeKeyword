package com.mashup.data.api.keyword

import android.content.Context
import com.mashup.data.db.KeywordModel
import com.mashup.data.db.MyPreferences
import com.mashup.domain.entities.Keyword
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class LocalKeywordServiceImpl @Inject constructor(
   @ActivityContext  private val context: Context,
   private val keywordModel: KeywordModel
) : KeywordService {
    override suspend fun getKeyword(): Keyword =
            Keyword((MyPreferences.getKeyword(context)))

    override suspend fun requestNewKeyword(): Keyword {
        val newKeyword = keywordModel.getKeyword(context).apply {
            MyPreferences.setKeyword(context, this)
        }
        return Keyword(newKeyword)
    }

}