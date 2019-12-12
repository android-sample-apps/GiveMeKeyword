package com.mut_jaeryo.givmkeyword.utills.Database

import android.content.Context
import android.content.SharedPreferences

import androidx.preference.PreferenceManager


class BasicDB {

    companion object
    {
        val PREF_KETWORD = "Keyword"

        fun getSharedPreferences(ctx : Context) : SharedPreferences
        {
            return PreferenceManager.getDefaultSharedPreferences(ctx)
        }

        fun getKeyword(ctx : Context): String?
        {
           return getSharedPreferences(ctx).getString(PREF_KETWORD,"가로등 밑에서 비를 맞고 있는 사람")
        }

        fun setKeyword(ctx: Context, keyword : String)
        {
            val editor:SharedPreferences.Editor = getSharedPreferences(ctx).edit()
            editor.putString(PREF_KETWORD,keyword)
            editor.apply()
        }

    }
}