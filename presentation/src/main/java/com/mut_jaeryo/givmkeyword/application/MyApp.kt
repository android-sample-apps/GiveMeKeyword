package com.mut_jaeryo.givmkeyword.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.mut_jaeryo.givmkeyword.utills.Database.BasicDB


class MyApp : Application() {
    companion object { //kotlin에는 static이 없고 companion으로 선언해준다.
        val CANNEL_ID = "getKeyword"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                    CANNEL_ID,
                    "getKeyword_Service",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            if (!BasicDB.getSound(applicationContext)) {
                serviceChannel.setSound(null, null)
            }
            if (BasicDB.getVibradtion(applicationContext)) {
                serviceChannel.vibrationPattern = longArrayOf(0, 500)
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}