package com.mut_jaeryo.givmkeyword.utills.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.mut_jaeryo.givmkeyword.utills.receivers.AlertReceiver

import java.util.*

class SendAlert {

    companion object { //kotlin에는 static이 없고 companion으로 선언해준다.

        fun setAlert(context: Context, time: GregorianCalendar) {
            val intent: Intent = Intent(context, AlertReceiver::class.java)

            val pending: PendingIntent = PendingIntent.getBroadcast(
                    context,
                    777,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            )

            val manager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time.timeInMillis, pending)
            } else {

                manager[AlarmManager.RTC_WAKEUP, time.timeInMillis] = pending
            }
        }
    }

}