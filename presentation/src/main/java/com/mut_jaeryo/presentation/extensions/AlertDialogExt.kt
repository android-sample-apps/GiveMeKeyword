package com.mut_jaeryo.presentation.extensions

import android.app.Activity
import cn.pedant.SweetAlert.SweetAlertDialog

fun Activity.rewardAlert(listener: SweetAlertDialog.OnSweetClickListener) {
    SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("광고")
            .setContentText("광고를 보고 새로운 주제를 받으시겠습니까?")
            .setConfirmText("광고 보기")
            .setCancelText("취소")
            .setConfirmClickListener(listener)
            .show()
}

fun Activity.titleAlert(title: String, content: String) {
    SweetAlertDialog(this)
            .setTitleText(title)
            .setContentText(content)
            .show()
}

fun Activity.basicAlert(content: String) {
    SweetAlertDialog(this)
            .setContentText(content)
            .show()
}


fun Activity.errorAlert(content: String) {
    SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
            .setContentText(content)
            .show()
}

fun Activity.successAlert(content: String) {
    SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
            .setContentText(content)
            .show()
}