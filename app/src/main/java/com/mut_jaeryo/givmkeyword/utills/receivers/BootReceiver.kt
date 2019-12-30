package com.mut_jaeryo.givmkeyword.utills.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mut_jaeryo.givmkeyword.utills.Database.BasicDB
import com.mut_jaeryo.givmkeyword.utills.services.SendAlert
import java.util.*

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
      if(p1!!.action.equals(Intent.ACTION_BOOT_COMPLETED))
      {
          val gregorianCalendar: GregorianCalendar = GregorianCalendar()


          val date = BasicDB.getDate(p0!!)!!.split("-")

          val year :Int= date[0].toInt()
          val month :Int= date[1].toInt()
          val day = date[2].toInt()
          gregorianCalendar.set(Calendar.YEAR,year)
          gregorianCalendar.set(Calendar.MONTH,month-1)
          gregorianCalendar.set(Calendar.DAY_OF_MONTH,day)
          gregorianCalendar.set(Calendar.HOUR_OF_DAY,8)
          gregorianCalendar.set(Calendar.MINUTE,0)
          gregorianCalendar.set(Calendar.SECOND,0)

          SendAlert.setAlert(p0!!,gregorianCalendar)

      }
    }
}