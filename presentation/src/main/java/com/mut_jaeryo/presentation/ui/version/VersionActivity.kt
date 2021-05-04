package com.mut_jaeryo.presentation.ui.version

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import com.mut_jaeryo.presentation.R
import com.mut_jaeryo.presentation.databinding.ActivityVersionBinding
import com.tistory.blackjinbase.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VersionActivity :
        BaseActivity<ActivityVersionBinding>(R.layout.activity_version) {

    override var logTag: String = "VersionActivity"
    private val versionViewModel: VersionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.versionToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.viewModel = versionViewModel
        initAppVersion()
        initAppUpdateLink()
    }

    private fun initAppVersion() {
        val versionInfo = getAppVersion()
        setAppVersion(versionInfo)
        versionViewModel.checkAppVersion(versionInfo)
    }

    private fun setAppVersion(versionInfo: String) {
        binding.versionCurrentVersion.text = getString(R.string.version_info, versionInfo)
    }

    private fun getAppVersion(): String {
        return try {
            val packageInfo = packageManager.getPackageInfo(
                    packageName, 0
            )
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(logTag, e.message ?: "PackageManagerName Not Found")
            getString(R.string.version_name_not_found)
        }
    }

    private fun initAppUpdateLink() {
        binding.versionUpdateButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                        "https://play.google.com/store/apps/details?id=com.mut_jaeryo.givmkeyword")
                setPackage("com.android.vending")
            }
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
