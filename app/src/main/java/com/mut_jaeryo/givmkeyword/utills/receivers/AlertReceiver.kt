package com.mut_jaeryo.givmkeyword.utills.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mut_jaeryo.givmkeyword.utills.Database.BasicDB
import com.mut_jaeryo.givmkeyword.utills.keywords.Keyword
import com.mut_jaeryo.givmkeyword.utills.services.SendAlert
import com.mut_jaeryo.givmkeyword.utills.services.ShowNotify
import java.util.*

class AlertReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {


        val now = GregorianCalendar()
        now[Calendar.MINUTE] = 0
        now.add(Calendar.DAY_OF_MONTH, 1)
        SendAlert.setAlert(p0!!, now) //7일 뒤에 다시 설정


        //키워드 변경
        val keyword = Keyword.getKeyword(p0)
        BasicDB.setKeyword(p0,keyword)

        val service = Intent(p0, ShowNotify::class.java)
        p0.startService(service)
    }
}