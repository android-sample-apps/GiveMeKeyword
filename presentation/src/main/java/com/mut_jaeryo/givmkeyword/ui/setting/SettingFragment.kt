package com.mut_jaeryo.givmkeyword.ui.setting

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.mut_jaeryo.givmkeyword.R
import com.mut_jaeryo.givmkeyword.databinding.FragmentSettingBinding
import com.mut_jaeryo.givmkeyword.ui.opensource.OpenSourceActivity
import com.mut_jaeryo.givmkeyword.utils.AlertUtills
import com.mut_jaeryo.givmkeyword.utils.database.DrawingDB
import com.tistory.blackjinbase.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    override var logTag: String = "SettingFragment"

    private val settingViewModel: SettingViewModel by viewModels()
    private var nameEditDialog: AlertDialog? = null

    override fun onResume() {
        super.onResume()
        settingViewModel.loadUserName()
        settingViewModel.loadTodayWork()

        //TODO: drawingDB 리팩토링
        barSet = DrawingDB.db.getHistory()
        binding.barChart.animate(barSet)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBarChart()

        observeViewModel()
//        setting_alert_sound_switch.isChecked = Preference.getSound(context!!)
//
//        setting_alert_vibration_switch.isChecked = Preference.getVibradtion(context!!)
//
//        val switchListener = CompoundButton.OnCheckedChangeListener { p0, p1 ->
//            when (p0!!.id) {
//                R.id.setting_alert_sound_switch -> Preference.setSound(context!!, p1)
//
//                else -> Preference.setVibradtion(context!!, p1)
//            }
//        }
//        setting_alert_vibration_switch.setOnCheckedChangeListener(switchListener)
//        setting_alert_sound_switch.setOnCheckedChangeListener(switchListener)

        binding.settingOpenSource.setOnClickListener {
            val intent = Intent(activity, OpenSourceActivity::class.java)
            startActivity(intent)
        }
        binding.settingProfileNameEdit.setOnClickListener {
            showNameEditDialog()
        }
    }

    private fun initBarChart() {
        binding.barChart.apply {
            animation.duration = animationDuration
            labelsFormatter = { "${it.roundToInt()}" }
        }
    }

    private fun observeViewModel() {
        settingViewModel.userEditAvailable.observe(viewLifecycleOwner) {
            if (it)  binding.settingProfileNameEdit.visibility = View.GONE
        }
        settingViewModel.errorMessage.observe(this) {
            AlertUtills.ErrorAlert(requireContext(), it)
            nameEditDialog?.dismiss()
        }
    }

    private fun showNameEditDialog() {
        val viewInflated = LayoutInflater.from(context).inflate(R.layout.name_input_layout, view as ViewGroup, false)
        val input = viewInflated.findViewById(R.id.input) as EditText
        nameEditDialog = AlertDialog.Builder(context)
                .setTitle(getString(R.string.setting_dialog_title))
                .setView(viewInflated)
                .setPositiveButton("OK") { dialogInterface, _ ->
                    val name = input.text.toString()
                    if (name == "" || name == "이름 미정") Toast.makeText(context, "올바른 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                    else {
                        settingViewModel.createUser(name)
                        dialogInterface.dismiss()
                    }
                }
                .setNegativeButton("CANCEL") { dialogInterface, _ -> dialogInterface.dismiss() }
                .create()
        nameEditDialog?.show()
    }

    companion object {
        var barSet = linkedMapOf(
                "JAN" to 10F,
                "FEB" to 9F,
                "MAR" to 2F,
                "MAY" to 5F,
                "APR" to 1F,
                "JUN" to 3F
        )
        private const val animationDuration = 1000L
    }
}
