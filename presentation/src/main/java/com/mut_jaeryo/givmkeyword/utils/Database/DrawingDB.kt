package com.mut_jaeryo.givmkeyword.utils.Database

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.mut_jaeryo.givmkeyword.utils.DrawingUtils
import java.util.*
import kotlin.collections.LinkedHashMap

class DrawingDB() {

    companion object {
        const val visionCode: Int = 1
        var DrawingDB: SQLiteDatabase? = null
        var HeartDB: SQLiteDatabase? = null
        private var mDBHelper: DatabaseHelper? = null
        private var mCtx: Context? = null
        lateinit var db: DrawingDB
    }

    constructor(context: Context) : this() {
        mCtx = context
    }

    private inner class DatabaseHelper(val mCtx: Context, val name: String, val factory: SQLiteDatabase.CursorFactory?, val version: Int) : SQLiteOpenHelper(mCtx, name, factory, version) {


        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(DrawingTable._create)
            p0.execSQL(HeartTable._create)
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("DROP TABLE IF EXISTS " + DrawingTable.Table_Name)
            p0.execSQL("DROP TABLE IF EXISTS " + HeartTable.Table_Name)
            onCreate(p0)
        }

    }


    fun DrawingInsert(id: String, content: String, date: String) {

        DrawingDB!!.execSQL("INSERT INTO  ${DrawingTable.Table_Name} VALUES ('$id','$content','$date');") // string은 값은 '이름' 처럼 따음표를 붙여줘야함
    }

//    fun getMyDrawing(): ArrayList<drawingItem>
//    {
//        val array = ArrayList<drawingItem>()
//
//        val cursor: Cursor = DrawingDB!!.rawQuery("select * from ${DrawingTable.Table_Name} ", null)
//
//        val name:String = BasicDB.getName(mCtx!!)!!
//
//        while(cursor.moveToNext())
//        {
//            array.add(drawingItem(cursor.getString(0),name,cursor.getString(1)))
//        }
//
//        cursor.close()
//        return array
//    }

    fun getHistory(): LinkedHashMap<String, Float> {

        val linkHash = linkedMapOf<String, Float>()

        for (index in 0..6) {
            val date = GregorianCalendar()
            date.add(Calendar.DAY_OF_MONTH, -(6 - index))

            val weekDay: String = if (index != 6) DrawingUtils.getDayOfWeek(date.get(Calendar.DAY_OF_WEEK))
            else "오늘"

            Log.d("week", weekDay)
            val date_s = "${date.get(Calendar.YEAR)}-${date.get(Calendar.MONTH) + 1}-${date.get(Calendar.DAY_OF_MONTH)}"

            val cursor: Cursor = DrawingDB!!.rawQuery("select * from ${DrawingTable.Table_Name} where date = '$date_s'", null)

            linkHash[weekDay] = cursor.count.toFloat()

            cursor.close()
        }
        return linkHash
    }

    fun getMyHeart(id: String): Boolean {

        val cursor: Cursor = HeartDB!!.rawQuery("select * from ${HeartTable.Table_Name} where id = '$id'", null)
        return cursor.count > 0
    }

    fun changeHeart(id: String, isHeart: Boolean) {
        if (isHeart) {
            //제거
            HeartDB!!.execSQL("DELETE FROM ${HeartTable.Table_Name} where id = '$id';")
        } else { //추가
            HeartDB!!.execSQL("INSERT INTO  ${HeartTable.Table_Name} VALUES ('$id');") // string은 값은 '이름' 처럼 따음표를 붙여줘야함
        }
    }

    @Throws(SQLException::class)
    fun open(): DrawingDB? {
        mDBHelper = DatabaseHelper(mCtx!!, "${DrawingTable.Table_Name}.db", null, visionCode)
        DrawingDB = mDBHelper!!.writableDatabase
        mDBHelper = DatabaseHelper(mCtx!!, "${HeartTable.Table_Name}}.db", null, visionCode)
        HeartDB = mDBHelper!!.writableDatabase
        return this
    }

    fun close() { //3개 table 쓰기 모드 종료
        DrawingDB!!.close()
        HeartDB!!.close()
    }
}