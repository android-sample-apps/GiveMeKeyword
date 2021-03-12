package com.mut_jaeryo.givmkeyword.utills.keywords

import android.content.Context
import com.mut_jaeryo.givmkeyword.R


class Keyword {


    companion object { //kotlin에는 static이 없고 companion으로 선언해준다.

        fun getKeyword(context: Context): String {
            val keywordClass = Keyword()

            return when ((Math.random() * 2).toInt()) {
                0 -> {
                    val verb = keywordClass.getVerb(context)
                    "${keywordClass.getNounPlace(context)}에서 $verb"
                }
                else -> {
                    keywordClass.getQuestion(context)
                }
            }
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