package com.mut_jaeryo.givmkeyword.utills

import android.content.Context
import cn.pedant.SweetAlert.SweetAlertDialog


class AlertUtills {
    companion object {

        fun RewardAlert(context: Context, listener: SweetAlertDialog.OnSweetClickListener) {
            SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("광고")
                    .setContentText("광고를 보고 새로운 주제를 받으시겠습니까?")
                    .setConfirmText("광고 보기")
                    .setCancelText("취소")
                    .setConfirmClickListener(listener)
                    .show()
        }

        fun TitleAlert(context: Context, title: String, content: String) {
            SweetAlertDialog(context)
                    .setTitleText(title)
                    .setContentText(content)
                    .show()
        }

        fun BasicAlert(context: Context, content: String) {
            SweetAlertDialog(context)
                    .setContentText(content)
                    .show()
        }


        fun ErrorAlert(context: Context, content: String) {
            SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                    .setContentText(content)
                    .show()
        }

        fun SuccessAlert(context: Context, content: String) {
            SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                    .setContentText(content)
                    .show()
        }

    }
}