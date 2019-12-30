package com.mut_jaeryo.givmkeyword

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.mut_jaeryo.givmkeyword.utills.Database.BasicDB
import com.mut_jaeryo.givmkeyword.utills.Database.ImageSave
import com.mut_jaeryo.givmkeyword.utills.services.SendAlert
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : FragmentActivity() {

    private lateinit var mPager:ViewPager2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!BasicDB.getInit(applicationContext)) //알람 설정
        {
            SendAlert.setAlert(applicationContext, GregorianCalendar())
        }

        mPager = findViewById(R.id.main_pager)

       mPager.isUserInputEnabled = false
        val arrayList:ArrayList<Fragment> = ArrayList()
        arrayList.add(TodayGoalFragment())
        arrayList.add(StoryFragment())
        arrayList.add(SettingFragment())

        val adapter:ScreenSlidePagerAdapter = ScreenSlidePagerAdapter(this,arrayList)

        mPager.adapter = adapter
        val tabLayout:TabLayout = findViewById(R.id.main_tabLayout)
        tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                val position = p0!!.position
                p0.text = ""
                val icon:Int
                when(position){
                    0 -> icon = R.drawable.draw_utility
                    1 -> icon = R.drawable.story
                    else -> icon = R.drawable.settings
                }
                p0.setIcon(icon)
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val position = p0!!.position
                mPager.currentItem = position

                val icon:Int
                when(position){
                    0 -> {
                        icon = R.drawable.draw_utility_white
                        p0.text = "그림"
                    }
                    1 -> {
                        icon = R.drawable.story_white
                        p0.text = "스토리"
                    }
                    else -> {
                        icon = R.drawable.settings_white
                        p0.text = "설정"
                    }
                }
                p0.setIcon(icon)
            }

        })

    }

    override fun onBackPressed() {
        if(mPager.currentItem == 0 )
        {
            super.onBackPressed()
        }else
        {
            mPager.currentItem = mPager.currentItem-1
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ImageSave.drawingImage = null
    }

    private inner class ScreenSlidePagerAdapter(fa:FragmentActivity,val array:ArrayList<Fragment>) :FragmentStateAdapter(fa){
        override fun createFragment(position: Int): Fragment = array[position]

        override fun getItemCount(): Int = array.size
    }
}
