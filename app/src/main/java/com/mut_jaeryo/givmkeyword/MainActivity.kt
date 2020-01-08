package com.mut_jaeryo.givmkeyword

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.mut_jaeryo.givmkeyword.utills.Database.BasicDB
import com.mut_jaeryo.givmkeyword.utills.Database.DrawingDB
import com.mut_jaeryo.givmkeyword.utills.Database.ImageSave
import com.mut_jaeryo.givmkeyword.utills.services.SendAlert
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : FragmentActivity() {

    private lateinit var mPager: ViewPager2

    private lateinit var tabLayout: TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!BasicDB.getInit(applicationContext)) //알람 설정
        {
            SendAlert.setAlert(applicationContext, GregorianCalendar())
        }

        DrawingDB.db = DrawingDB(applicationContext)
        DrawingDB.db.open()

        mPager = findViewById(R.id.main_pager)

        mPager.isUserInputEnabled = false
        val arrayList: ArrayList<Fragment> = ArrayList()
        arrayList.add(TodayGoalFragment())
        arrayList.add(StoryFragment())
        arrayList.add(SettingFragment())

        val adapter: ScreenSlidePagerAdapter = ScreenSlidePagerAdapter(this, arrayList)

        mPager.adapter = adapter
        tabLayout = findViewById(R.id.main_tabLayout)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                val position = p0!!.position
                p0.text = ""
                val icon: Int
                when (position) {
                    0 -> icon = R.drawable.draw_utility
                    1 -> icon = R.drawable.story
                    else -> icon = R.drawable.settings
                }
                p0.setIcon(icon)
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val position = p0!!.position
                mPager.currentItem = position

                val icon: Int
                when (position) {
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

    public fun goToEditName()
    {
        tabLayout.getTabAt(2)!!.select()
    }
    override fun onBackPressed() {

        AlertDialog.Builder(this).setMessage("나가면 그림이 저장되지 않습니다. \n 나가시겠습니까?")
                .setPositiveButton("나가기"){ _: DialogInterface, i: Int ->
                    super.onBackPressed()
                }.setNegativeButton("취소"){_: DialogInterface, i: Int ->

                }.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        ImageSave.drawingImage = null
        DrawingDB.db.close()
    }

    private inner class ScreenSlidePagerAdapter(fa:FragmentActivity,val array:ArrayList<Fragment>) :FragmentStateAdapter(fa){
        override fun createFragment(position: Int): Fragment = array[position]

        override fun getItemCount(): Int = array.size
    }
}
