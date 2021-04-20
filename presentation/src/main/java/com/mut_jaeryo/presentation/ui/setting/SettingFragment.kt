package com.mut_jaeryo.presentation.ui.setting

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.mut_jaeryo.presentation.R
import com.mut_jaeryo.presentation.databinding.FragmentSettingBinding
import com.mut_jaeryo.presentation.utils.NotificationUtil
import com.tistory.blackjinbase.base.BaseFragment
import com.tistory.blackjinbase.ext.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override var logTag: String = "SettingFragment"

    private val settingViewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = settingViewModel

        binding.settingNotificationSetting.apply {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                    putExtra(Settings.EXTRA_CHANNEL_ID, NotificationUtil.CHANNEL_ID)
                }
                startActivity(intent)
            } else {
                visibility = View.GONE
            }
        }

        binding.settingProfileNameEdit.setOnClickListener {
            showNameEditDialog()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        settingViewModel.errorMessage.observe(this) {
            toast(it)
        }
        settingViewModel.userName.observe(this) {
            binding.settingProfileNameEdit.visibility =
                    if (it == "이름 미정") View.VISIBLE else View.GONE
        }
    }

    private fun showNameEditDialog() {
        val viewInflated = LayoutInflater.from(context).inflate(R.layout.alert_dialog, view as ViewGroup, false)

        val input = viewInflated.findViewById(R.id.input) as EditText
        val builder = AlertDialog.Builder(context)
                .setTitle(getString(R.string.setting_dialog_title))
                .setView(viewInflated)
                .setPositiveButton("OK") { dialogInterface, _ ->
                    val name = input.text.toString()
                    if (name.isEmpty()) {
                        toast(R.string.setting_name_empty_message)
                    } else {
                        settingViewModel.createUser(name)
                    }
                    dialogInterface.dismiss()
                }
                .setNegativeButton("CANCEL") { dialogInterface, _ -> dialogInterface.dismiss() }
                .create()
        builder.show()
    }
}


