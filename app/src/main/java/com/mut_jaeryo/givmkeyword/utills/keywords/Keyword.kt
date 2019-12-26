package com.mut_jaeryo.givmkeyword.utills.keywords

import android.content.Context
import com.mut_jaeryo.givmkeyword.R


class Keyword {


    companion object{ //kotlin에는 static이 없고 companion으로 선언해준다.

        fun getKeyword(context:Context):String
        {

            val keywordClass = Keyword()

            var verb = keywordClass.getVerb(context)
            return "${keywordClass.getNounPlace(context)}에서 $verb"
        }

    }

    fun getNounPlace(context: Context):String{

        val nounPlaceArray = context.resources.getStringArray(R.array.nounOfPlace_array)

        val index:Int= (Math.random()*nounPlaceArray.size).toInt()
        return nounPlaceArray[index]
    }

    fun getVerb(context : Context):String{
        val verbArray = context.resources.getStringArray(R.array.nounOfVerb_array)

        val index:Int= (Math.random()*verbArray.size).toInt()
        return verbArray[index]
    }
}