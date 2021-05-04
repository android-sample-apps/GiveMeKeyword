package com.mut_jaeryo.presentation.ui.setting

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.mut_jaeryo.presentation.R
import com.mut_jaeryo.presentation.databinding.FragmentSettingBinding
import com.mut_jaeryo.presentation.ui.opensource.OpenSourceActivity
import com.mut_jaeryo.presentation.ui.version.VersionActivity
import com.tistory.blackjinbase.base.BaseFragment
import com.tistory.blackjinbase.ext.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override var logTag: String = "SettingFragment"

    private val settingViewModel: SettingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = settingViewModel

        initView()
        observeViewModel()
    }

    private fun initView() {
        binding.apply {
            settingProfileNameEdit.setOnClickListener {
                showNameEditDialog()
            }
            settingOpenSource.setOnClickListener {
                val intent = Intent(requireContext(), OpenSourceActivity::class.java)
                startActivity(intent)
            }
            settingAppVersion.setOnClickListener {
                val intent = Intent(requireContext(), VersionActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun observeViewModel() {
        settingViewModel.errorMessage.observe(viewLifecycleOwner) {
            toast(it)
        }
    }

    private fun showNameEditDialog() {
        val viewInflated = LayoutInflater.from(context).inflate(R.layout.dailog_name_change, view as ViewGroup, false)

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


