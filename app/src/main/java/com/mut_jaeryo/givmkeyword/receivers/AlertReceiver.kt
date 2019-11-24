package com.mut_jaeryo.givmkeyword.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mut_jaeryo.givmkeyword.services.sendAlert
import java.util.*

class AlertReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {


        val now = GregorianCalendar()
        now[Calendar.MINUTE] = 0
        now.add(Calendar.DAY_OF_MONTH, 1)
        sendAlert.setAlert(p0!!, now) //7일 뒤에 다시 설정

    }
}