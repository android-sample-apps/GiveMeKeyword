package com.mut_jaeryo.givmkeyword.utills.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.mut_jaeryo.givmkeyword.application.MyApp
import com.mut_jaeryo.givmkeyword.MainActivity
import com.mut_jaeryo.givmkeyword.R

class ShowNotify : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val notificationIntent = Intent(this, MainActivity::class.java)

        intent!!.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0) //알람을 눌렀을 때 해당 엑티비티로


        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        val context = "오늘의 그림 키워드를 확인해보세요"


        val builder = NotificationCompat.Builder(this, MyApp.CANNEL_ID)
                .setContentTitle("키워드")
                .setContentText(context)
                .setSmallIcon(R.drawable.alert_icon)
                .setContentIntent(pendingIntent)

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(33, builder.build())



        return START_NOT_STICKY
    }
}