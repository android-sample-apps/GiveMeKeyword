package com.mut_jaeryo.givmkeyword.utills.Database

import android.content.Context
import android.content.SharedPreferences

import androidx.preference.PreferenceManager


class BasicDB {

    companion object
    {
        val PREF_KETWORD = "Keyword"
        val PREF_NAME = "name"
        val PREF_DATE = "Date"
        val PREF_INIT = "init"

        fun getSharedPreferences(ctx : Context) : SharedPreferences
        {
            return PreferenceManager.getDefaultSharedPreferences(ctx)
        }

        fun getKeyword(ctx : Context): String?
        {
           return getSharedPreferences(ctx).getString(PREF_KETWORD,"가로등 밑에서 비를 맞고 있는 사람")
        }

        fun setName(ctx: Context, name : String)
        {
            val editor:SharedPreferences.Editor = getSharedPreferences(ctx).edit()
            editor.putString(PREF_NAME,name)
            editor.apply()
        }

        fun setInit(ctx: Context, init : Boolean)
        {
            val editor:SharedPreferences.Editor = getSharedPreferences(ctx).edit()
            editor.putBoolean(PREF_INIT,init)
            editor.apply()
        }

        fun getInit(ctx : Context): Boolean
        {
            return getSharedPreferences(ctx).getBoolean(PREF_INIT,false)
        }

        fun getName(ctx : Context): String?
        {
            return getSharedPreferences(ctx).getString(PREF_NAME,"알수 없음")
        }

        fun setDate(ctx: Context, date : String)
        {
            val editor:SharedPreferences.Editor = getSharedPreferences(ctx).edit()
            editor.putString(PREF_DATE,date)
            editor.apply()
        }

        fun getDate(ctx : Context): String?
        {
            return getSharedPreferences(ctx).getString(PREF_DATE,"2019-12-19")
        }

        fun setKeyword(ctx: Context, keyword : String)
        {
            val editor:SharedPreferences.Editor = getSharedPreferences(ctx).edit()
            editor.putString(PREF_KETWORD,keyword)
            editor.apply()
        }

    }
}