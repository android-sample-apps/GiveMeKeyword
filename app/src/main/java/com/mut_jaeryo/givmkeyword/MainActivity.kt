package com.mut_jaeryo.givmkeyword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout


class MainActivity : FragmentActivity() {

    private lateinit var mPager:ViewPager2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPager = findViewById(R.id.main_pager)

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

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                mPager.currentItem = p0?.position ?: 0
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

    private inner class ScreenSlidePagerAdapter(fa:FragmentActivity,val array:ArrayList<Fragment>) :FragmentStateAdapter(fa){
        override fun createFragment(position: Int): Fragment = array[position]

        override fun getItemCount(): Int = array.size
    }
}
