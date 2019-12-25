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
        val PREF_ID = "user_id"
        val PREF_SOUND = "sound"
        val PREF_VIB = "vibradtion"


        fun getSharedPreferences(ctx : Context) : SharedPreferences
        {
            return PreferenceManager.getDefaultSharedPreferences(ctx)
        }

        fun getKeyword(ctx : Context): String?
        {
           return getSharedPreferences(ctx).getString(PREF_KETWORD,"가로등 밑에서 비를 맞고 있는 사람")
        }

        fun getSound(ctx : Context): Boolean
        {
            return getSharedPreferences(ctx).getBoolean(PREF_SOUND,true)
        }

        fun getVibradtion(ctx: Context): Boolean
        {
            return getSharedPreferences(ctx).getBoolean(PREF_VIB,true)
        }

        fun setSound(ctx: Context,isSound : Boolean)
        {
            val editor:SharedPreferences.Editor = getSharedPreferences(ctx).edit()
            editor.putBoolean(PREF_SOUND,isSound)
            editor.apply()
        }

        fun setVibradtion(ctx: Context,isVib: Boolean)
        {
            val editor:SharedPreferences.Editor = getSharedPreferences(ctx).edit()
            editor.putBoolean(PREF_VIB,isVib)
            editor.apply()
        }

        fun setName(ctx: Context, name : String)
        {
            val editor:SharedPreferences.Editor = getSharedPreferences(ctx).edit()
            editor.putString(PREF_NAME,name)
            editor.apply()
        }

        fun setId(ctx: Context, id:String)
        {
            val editor:SharedPreferences.Editor = getSharedPreferences(ctx).edit()
            editor.putString(PREF_ID,id)
            editor.apply()
        }

        fun getId(ctx : Context): String?
        {
            return getSharedPreferences(ctx).getString(PREF_ID,"")
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
            return getSharedPreferences(ctx).getString(PREF_NAME,"이름 미정")
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