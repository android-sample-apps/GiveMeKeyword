package com.mut_jaeryo.givmkeyword.database

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class KeywordDB() {

    companion object
    {
        const val visionCode:Int = 1
        var KeywordDB: SQLiteDatabase? = null
        private var mDBHelper: DatabaseHelper? = null
        private var mCtx: Context? = null
    }

    constructor(context: Context):this()
    {
        mCtx=context
    }
    private inner class  DatabaseHelper(val mCtx:Context,val name:String,val factory:SQLiteDatabase.CursorFactory?,val version:Int) : SQLiteOpenHelper(mCtx,name,factory,version) {



        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(KeywordTable._create)
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("DROP TABLE IF EXISTS " + KeywordTable.Table_Name)
            onCreate(p0)
        }
    }



    @Throws(SQLException::class)
    fun open(): KeywordDB? {
        mDBHelper = DatabaseHelper(mCtx!!, "${KeywordTable.Table_Name}.db", null, visionCode)
        KeywordDB= mDBHelper!!.writableDatabase

        return this
    }

    fun close() { //3개 table 쓰기 모드 종료
       KeywordDB!!.close()
    }
}