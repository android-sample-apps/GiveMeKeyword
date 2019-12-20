package com.mut_jaeryo.givmkeyword


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        barChart.animation.duration = animationDuration
        barChart.animate(barSet)
    }

    companion object {



        private val barSet = linkedMapOf(
                "JAN" to 10F,
                "FEB" to 9F,
                "MAR" to 2F,
                "MAY" to 5F,
                "APR" to 1F,
                "JUN" to 3F
        )


        private val animationDuration = 1000L
    }

}
