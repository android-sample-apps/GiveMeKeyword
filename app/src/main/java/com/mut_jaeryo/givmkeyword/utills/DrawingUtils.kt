package com.mut_jaeryo.givmkeyword.utills

class DrawingUtils {
    companion object{
        fun getDayOfWeek(position:Int):String = when(position) {

            1 -> "일"

            2 -> "월"

            3 -> "화"

            4 -> "수"

            5 -> "목"

            6 -> "금"

            else -> "토"
        }
    }
}