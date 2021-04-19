package com.mut_jaeryo.presentation.utils

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.mut_jaeryo.presentation.R
import com.mut_jaeryo.presentation.receiver.AlarmReceiver
import com.mut_jaeryo.presentation.ui.main.MainActivity
import java.util.*

object NotificationUtil {
    const val CHANNEL_ID = "getKeyword"
    private const val NOTIFICATION_ID = 777

    fun NotificationManager.showNotify(applicationContext: Context) {
        val notificationIntent = Intent(applicationContext, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, notificationIntent, 0)

        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setContentTitle(applicationContext.getString(R.string.notification_title))
                .setContentText(applicationContext.getString(R.string.notification_description))
                .setSmallIcon(R.drawable.alert_icon)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        notify(NOTIFICATION_ID, builder.build())
    }
}