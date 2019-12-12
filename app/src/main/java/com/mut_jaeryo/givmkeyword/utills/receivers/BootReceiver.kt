package com.mut_jaeryo.givmkeyword.utills.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mut_jaeryo.givmkeyword.utills.services.SendAlert
import java.util.*

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
      if(p1!!.action.equals(Intent.ACTION_BOOT_COMPLETED))
      {
          val gregorianCalendar: GregorianCalendar = GregorianCalendar()

          gregorianCalendar.add(Calendar.DAY_OF_MONTH,1)
          gregorianCalendar.set(Calendar.HOUR_OF_DAY,8)
          gregorianCalendar.set(Calendar.MINUTE,0)
          gregorianCalendar.set(Calendar.SECOND,0)

          SendAlert.setAlert(p0!!,gregorianCalendar)

      }
    }
}