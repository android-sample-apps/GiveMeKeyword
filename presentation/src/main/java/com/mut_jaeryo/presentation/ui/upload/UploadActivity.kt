package com.mut_jaeryo.presentation.ui.upload

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
import com.mut_jaeryo.presentation.R
import com.mut_jaeryo.presentation.databinding.ActivityUploadBinding
import com.mut_jaeryo.presentation.utils.errorAlert
import com.mut_jaeryo.presentation.utils.successAlert
import com.tistory.blackjinbase.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    override fun onResume() {
        super.onResume()
        uploadViewModel.getUserNameAsync()
    }

    private fun observeViewModel() {
        uploadViewModel.isUploadSuccess.observe(this) {
            if (it) {
                successAlert(getString(R.string.upload_success))
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
        errorAlert(message)
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
