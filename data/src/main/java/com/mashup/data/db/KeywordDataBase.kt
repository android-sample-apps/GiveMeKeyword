package com.mashup.data.db

import android.content.Context
import com.mashup.data.R

object KeywordDataBase {
    fun getKeyword(context: Context) = when ((Math.random() * 2).toInt()) {
        0 -> {
            val verb = getVerb(context)
            "${getNounPlace(context)}에서 $verb"
        }
        else -> {
            getQuestion(context)
        }
    }

    private fun getQuestion(context: Context): String {
        val questionArray = context.resources.getStringArray(R.array.question_array)

        val index: Int = (Math.random() * questionArray.size).toInt()
        return questionArray[index]
    }

    private fun getNounPlace(context: Context): String {

        val nounPlaceArray = context.resources.getStringArray(R.array.nounOfPlace_array)

        val index: Int = (Math.random() * nounPlaceArray.size).toInt()
        return nounPlaceArray[index]
    }

    private fun getVerb(context: Context): String {
        val verbArray = context.resources.getStringArray(R.array.nounOfVerb_array)

        val index: Int = (Math.random() * verbArray.size).toInt()
        return verbArray[index]
    }
}