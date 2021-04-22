package com.mut_jaeryo.presentation.service

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.content.ContextCompat
import com.mut_jaeryo.presentation.utils.NotificationUtil.showNotify

class NotificationService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationManager = ContextCompat.getSystemService(
                applicationContext,
                NotificationManager::class.java)
        as NotificationManager
        notificationManager.showNotify(applicationContext)
        return START_NOT_STICKY
    }
}