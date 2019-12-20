package com.mut_jaeryo.givmkeyword.utills.Database

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mut_jaeryo.givmkeyword.view.Items.drawingItem

class DrawingDB() {

    companion object
    {
        const val visionCode:Int = 1
        var DrawingDB: SQLiteDatabase? = null
        private var mDBHelper: DatabaseHelper? = null
        private var mCtx: Context? = null
    }

    constructor(context: Context):this()
    {
        mCtx =context
    }
    private inner class  DatabaseHelper(val mCtx:Context,val name:String,val factory:SQLiteDatabase.CursorFactory?,val version:Int) : SQLiteOpenHelper(mCtx,name,factory,version) {



        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(DrawingTable._create)
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("DROP TABLE IF EXISTS " + DrawingTable.Table_Name)
            onCreate(p0)
        }

    }


    fun DrawingInsert(id:String,content:String,date:String) {

        DrawingDB!!.execSQL("INSERT INTO  $DrawingTable.Table_Name  VALUES ('$id','$content','$date');") // string은 값은 '이름' 처럼 따음표를 붙여줘야함
    }

    fun getMyDrawing(): ArrayList<drawingItem>
    {
        var array = ArrayList<drawingItem>()

        val cursor: Cursor = DrawingDB!!.rawQuery("select * from $DrawingTable.Table_Name ", null)

        val name:String = BasicDB.getName(mCtx!!)!!

        while(cursor.moveToNext())
        {
            array.add(drawingItem(cursor.getString(0),name,cursor.getString(1)))
        }


        cursor.close()
        return array
    }


    @Throws(SQLException::class)
    fun open(): DrawingDB? {
        mDBHelper = DatabaseHelper(mCtx!!, "${DrawingTable.Table_Name}.db", null, visionCode)
        DrawingDB = mDBHelper!!.writableDatabase

        return this
    }

    fun close() { //3개 table 쓰기 모드 종료
       DrawingDB!!.close()
    }
}