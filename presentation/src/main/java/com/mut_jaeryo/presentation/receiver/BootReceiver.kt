package com.mut_jaeryo.presentation.receiver

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.mut_jaeryo.presentation.utils.AlarmUtil.setAlarm

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            context?.let { receiverContext ->
                val alarmManager = ContextCompat.getSystemService(
                        receiverContext,
                        AlarmManager::class.java
                ) as AlarmManager
                alarmManager.setAlarm(receiverContext)
            }
        }
    }
}