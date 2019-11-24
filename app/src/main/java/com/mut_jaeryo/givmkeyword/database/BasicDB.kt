package com.mut_jaeryo.givmkeyword.database

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
           return getSharedPreferences(ctx).getString(PREF_KETWORD,"")
        }

        fun setKeyword(ctx: Context, keyword : String)
        {
            val editor:SharedPreferences.Editor = getSharedPreferences(ctx).edit()
            editor.putString(PREF_KETWORD,keyword)
            editor.apply()
        }

    }
}