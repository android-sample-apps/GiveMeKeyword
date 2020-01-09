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

        fun TitleAlert(context: Context,title:String,content:String)
        {
            SweetAlertDialog(context)
                    .setTitleText(title)
                    .setContentText(content)
                    .show()
        }

        fun BasicAlert(context: Context,content:String)
        {
            SweetAlertDialog(context)
                    .setTitleText(content)
                    .show()
        }


        fun ErrorAlert(context:Context,content:String)
        {
            SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(content)
                    .show()
        }

        fun SuccessAlert(context:Context,content:String)
        {
            SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(content)
                    .show()
        }

    }
}