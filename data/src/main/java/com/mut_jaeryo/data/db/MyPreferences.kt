package com.mut_jaeryo.data.db

import android.content.Context
import android.content.SharedPreferences

import androidx.preference.PreferenceManager
import com.mut_jaeryo.data.R


object MyPreferences {
    private const val PREF_KEYWORD = "keyword"
    private const val PREF_NAME = "name"
    private const val PREF_DATE = "Date"
    private const val PREF_REQUEST_COUNT = "today_newKeywordCount"
    private const val PREF_SOUND = "sound"
    private const val PREF_VIB = "vibration"


    private fun getSharedPreferences(ctx: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun getKeyword(ctx: Context): String {
        return getSharedPreferences(ctx).getString(PREF_KEYWORD, ctx.getString(R.string.init_keyword)) ?: ""
    }

    fun getSound(ctx: Context): Boolean {
        return getSharedPreferences(ctx).getBoolean(PREF_SOUND, true)
    }

    fun getRequestCount(ctx: Context): Int {
        return getSharedPreferences(ctx).getInt(PREF_REQUEST_COUNT, 0)
    }

    fun getVibration(ctx: Context): Boolean {
        return getSharedPreferences(ctx).getBoolean(PREF_VIB, true)
    }

    fun setSound(ctx: Context, isSound: Boolean) {
        val editor: SharedPreferences.Editor = getSharedPreferences(ctx).edit()
        editor.putBoolean(PREF_SOUND, isSound)
        editor.apply()
    }

    fun setRequestKeywordCount(ctx: Context, count: Int) {
        val editor: SharedPreferences.Editor = getSharedPreferences(ctx).edit()
        editor.putInt(PREF_REQUEST_COUNT, count)
        editor.apply()
    }

    fun setVibration(ctx: Context, isVib: Boolean) {
        val editor: SharedPreferences.Editor = getSharedPreferences(ctx).edit()
        editor.putBoolean(PREF_VIB, isVib)
        editor.apply()
    }

    fun setName(ctx: Context, name: String) {
        val editor: SharedPreferences.Editor = getSharedPreferences(ctx).edit()
        editor.putString(PREF_NAME, name)
        editor.apply()
    }

    fun getName(ctx: Context): String? {
        return getSharedPreferences(ctx).getString(PREF_NAME, null)
    }

    fun setDate(ctx: Context, date: String) {
        val editor: SharedPreferences.Editor = getSharedPreferences(ctx).edit()
        editor.putString(PREF_DATE, date)
        editor.apply()
    }

    fun getDate(ctx: Context): String? {
        return getSharedPreferences(ctx).getString(PREF_DATE, null)
    }

    fun setKeyword(ctx: Context, keyword: String) {
        val editor: SharedPreferences.Editor = getSharedPreferences(ctx).edit()
        editor.putString(PREF_KEYWORD, keyword)
        editor.apply()
    }
}