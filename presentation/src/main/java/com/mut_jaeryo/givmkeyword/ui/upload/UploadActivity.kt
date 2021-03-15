package com.mut_jaeryo.givmkeyword.ui.upload

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
import com.mut_jaeryo.givmkeyword.R
import com.mut_jaeryo.givmkeyword.databinding.ActivityUploadBinding
import com.mut_jaeryo.givmkeyword.utils.AlertUtills
import com.tistory.blackjinbase.base.BaseActivity

class UploadActivity : BaseActivity<ActivityUploadBinding>(R.layout.activity_upload) {

    override var logTag: String = "UploadActivity"
    private val uploadViewModel: UploadViewModel by viewModels()
    private var loadingDialog: SweetAlertDialog ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.uploadToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        observeViewModel()
    }

    private fun observeViewModel() {
        uploadViewModel.isUploadSuccess.observe(this) {
            if (it) {
                AlertUtills.SuccessAlert(this, "저장에 성공했습니다")
                setResult(RESULT_OK)
                finish()
            }
        }
        uploadViewModel.uploadErrorMessage.observe(this) {
            showErrorMessage(it)
        }
        uploadViewModel.isUploading.observe(this) {
            if (it) {
                showLoadingDialog()
            } else {
                hideLoadingDialog()
            }
        }
    }

    private fun showErrorMessage(message: String) {
        AlertUtills.ErrorAlert(this, message)
    }

    private fun showLoadingDialog() {
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE).apply {
            progressHelper.barColor = Color.parseColor("#4285F4")
            titleText = "Uploading"
            setCancelable(false)
        }
        loadingDialog?.show()
    }

    private fun hideLoadingDialog() {
        loadingDialog?.dismissWithAnimation()
        loadingDialog = null
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
