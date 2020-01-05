package com.mut_jaeryo.givmkeyword



import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.mut_jaeryo.givmkeyword.utills.Database.BasicDB
import com.mut_jaeryo.givmkeyword.utills.Database.DrawingDB
import kotlinx.android.synthetic.main.fragment_setting.*


/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onResume() {
        super.onResume()

        barSet = DrawingDB.db.getHistory()
        barChart.animate(barSet)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        barChart.animation.duration = animationDuration


        if(BasicDB.getId(context!!) != "")
            setting_profile_name_edit.visibility = View.GONE

        setting_profile_name.text = BasicDB.getName(context!!)
        setting_alert_sound_switch.isChecked =  BasicDB.getSound(context!!)

        setting_alert_vibration_switch.isChecked = BasicDB.getSound(context!!)

        val  switchListner = CompoundButton.OnCheckedChangeListener { p0, p1 ->
            when(p0!!.id) {
                R.id.setting_alert_sound_switch -> BasicDB.setSound(context!!,p1)

                else -> BasicDB.setVibradtion(context!!,p1)
            }
        }

        setting_alert_vibration_switch.setOnCheckedChangeListener(switchListner)
        setting_alert_sound_switch.setOnCheckedChangeListener(switchListner)


        setting_profile_name_edit.setOnClickListener {
            showNameEditDialog()
        }

        setting_profile_name.text = BasicDB.getName(context!!)
    }

    private fun showNameEditDialog(){

        val builder = AlertDialog.Builder(context)

        builder.setTitle("이름 변경")
        val viewInflated = LayoutInflater.from(context).inflate(R.layout.name_input_layout,view as ViewGroup,false)

        val input = viewInflated.findViewById(R.id.input) as EditText

        builder.setView(viewInflated)
        builder.setPositiveButton("OK") { dialogInterface, _ ->

            val name = input.text.toString()
            if(name == "")Toast.makeText(context,"입력해주세요",Toast.LENGTH_SHORT).show()
            else {
                val db = FirebaseFirestore.getInstance().collection("users")
                val doc = db.document() //고유 id를 자동으로 생성


                BasicDB.setId(context!!,doc.id)
                val data = hashMapOf(
                        "name" to name,
                        "like" to 0
                )

                doc.set(data)
                        .addOnSuccessListener {
                            BasicDB.setName(context!!,name)
                            setting_profile_name.text = name
                            Toast.makeText(context,"이름이 변경되었습니다",Toast.LENGTH_LONG).show()
                        }
                        .addOnCanceledListener {   Toast.makeText(context,"잠시 후에 다시 시도해주세요",Toast.LENGTH_LONG).show() }

                dialogInterface.dismiss()
            }

        }
        builder.setNegativeButton("CANCEL") { dialogInterface , i-> dialogInterface.dismiss() }

        builder.show()
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
