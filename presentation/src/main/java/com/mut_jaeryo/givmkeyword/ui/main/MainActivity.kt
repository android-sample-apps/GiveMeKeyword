package com.mut_jaeryo.givmkeyword.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.gms.ads.MobileAds
import com.mut_jaeryo.givmkeyword.R
import com.mut_jaeryo.givmkeyword.SettingFragment
import com.mut_jaeryo.givmkeyword.StoryFragment
import com.mut_jaeryo.givmkeyword.ui.drawing.DrawingFragment
import com.mut_jaeryo.givmkeyword.databinding.ActivityMainBinding
import com.mut_jaeryo.givmkeyword.ui.upload.UploadActivity
import com.mut_jaeryo.givmkeyword.utils.services.SendAlert
import com.tistory.blackjinbase.base.BaseActivity
import com.tistory.blackjinbase.ext.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override var logTag: String = "MainActivity"

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!Preference.getInit(applicationContext)) {
            SendAlert.setAlert(applicationContext, GregorianCalendar())
        }
        initMobileAds()
        initViewPager()
        initTabLayout()
    }

    private fun initMobileAds() {
        MobileAds.initialize(this) { }
    }

    private fun initViewPager() {
        val fragmentList = arrayListOf(
                DrawingFragment(), StoryFragment(), SettingFragment()
        )
        val pagerAdapter = ScreenSlidePagerAdapter(this, fragmentList)
        binding.mainPager.apply {
            isUserInputEnabled = false
            adapter = pagerAdapter
        }
    }

    private fun initTabLayout() {
        binding.mainBottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.action_drawing -> {
                    binding.mainPager.currentItem = 0
                    true
                }
                R.id.action_story -> {
                    binding.mainPager.currentItem = 1
                    true
                }
                R.id.action_setting -> {
                    binding.mainPager.currentItem = 2
                    true
                }
                else -> false
            }
        }
    }

    fun goToEditName() {
        binding.mainPager.currentItem = 2
    }

    fun goToUpload() {
        if (mainViewModel.imageUrl.value != null) {
            val intent = Intent(this, UploadActivity::class.java).apply {
                putExtra("imageUrl", mainViewModel.imageUrl.value)
            }
            startActivity(intent)
        } else {
            toast("어플 결함으로 이미지 업로드에 실패했습니다. errorCode: 100")
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).setMessage("나가면 그림이 저장되지 않습니다. \n 나가시겠습니까?")
                .setPositiveButton("나가기") { _: DialogInterface, i: Int ->
                    super.onBackPressed()
                }.setNegativeButton("취소") { _: DialogInterface, i: Int ->

                }.show()
    }

    private class ScreenSlidePagerAdapter(fa: FragmentActivity, val fragmentList: ArrayList<Fragment>) : FragmentStateAdapter(fa) {
        override fun createFragment(position: Int): Fragment = fragmentList[position]

        override fun getItemCount(): Int = fragmentList.size
    }
}
