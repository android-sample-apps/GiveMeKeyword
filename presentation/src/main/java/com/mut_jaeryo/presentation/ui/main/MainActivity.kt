package com.mut_jaeryo.presentation.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.gms.ads.MobileAds
import com.mut_jaeryo.presentation.R
import com.mut_jaeryo.presentation.databinding.ActivityMainBinding
import com.mut_jaeryo.presentation.ui.drawing.DrawingFragment
import com.mut_jaeryo.presentation.ui.setting.SettingFragment
import com.mut_jaeryo.presentation.ui.story.StoryFragment
import com.mut_jaeryo.presentation.ui.upload.UploadActivity
import com.tistory.blackjinbase.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override var logTag: String = "MainActivity"

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO: 알람 설정

        initMobileAds()
        initViewPager()
        initTabLayout()

        observeViewModel()
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

    private fun observeViewModel() {
        mainViewModel.imageUrl.observe(this) {
            showUploadActivity(imageUrl = it)
        }
    }

    private fun showUploadActivity(imageUrl: String) {
        val intent = Intent(this@MainActivity, UploadActivity::class.java).apply {
            putExtra("imageUrl", imageUrl)
        }
        startActivity(intent)
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).setMessage("나가면 그림이 저장되지 않습니다. \n 나가시겠습니까?")
                .setPositiveButton("나가기") { _: DialogInterface, _: Int ->
                    super.onBackPressed()
                }.setNegativeButton("취소") { _: DialogInterface, _: Int ->
                }.show()
    }

    private class ScreenSlidePagerAdapter(fa: FragmentActivity, val fragmentList: List<Fragment>) : FragmentStateAdapter(fa) {
        override fun createFragment(position: Int): Fragment = fragmentList[position]

        override fun getItemCount(): Int = fragmentList.size
    }
}
