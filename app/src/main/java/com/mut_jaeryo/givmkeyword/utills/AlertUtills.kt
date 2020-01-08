package com.mut_jaeryo.givmkeyword.utills

import android.content.Context
import cn.pedant.SweetAlert.SweetAlertDialog


class AlertUtills {
    companion object{

        fun WaringDeleteAlert(context: Context,listener : SweetAlertDialog.OnSweetClickListener)
        {
            SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("주의")
                    .setContentText("삭제하면 되돌릴 수 없습니다.")
                    .setConfirmText("삭제했습니다!")
                    .setConfirmClickListener(listener)
                    .show()
        }

        fun ExistSameNameAlert(context: Context)
        {
            SweetAlertDialog(context)
                    .setTitleText("이름 중복")
                    .setContentText("동일한 이름의 사용자가 존재합니다!")
                    .show()
        }

        fun BasicAlert(context: Context,content:String)
        {
            SweetAlertDialog(context)
                    .setTitleText(content)
                    .show()
        }

    }
}