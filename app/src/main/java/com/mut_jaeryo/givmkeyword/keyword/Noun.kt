package com.mut_jaeryo.givmkeyword.keyword

import android.content.Context
import com.mut_jaeryo.givmkeyword.R


class Noun {

    companion object{ //kotlin에는 static이 없고 companion으로 선언해준다.

        fun getNoun(context: Context):String{
            val noun:String = ""

            val noumArray = context.resources.getStringArray(R.array.nounOfPerson_array)

            return noun
        }

    }
}