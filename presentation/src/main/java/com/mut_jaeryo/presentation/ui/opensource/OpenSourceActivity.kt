package com.mut_jaeryo.presentation.ui.opensource

import android.os.Bundle
import android.view.MenuItem
import com.mut_jaeryo.presentation.R
import com.mut_jaeryo.presentation.databinding.ActivityOpenSourceBinding
import com.tistory.blackjinbase.base.BaseActivity

class OpenSourceActivity :
        BaseActivity<ActivityOpenSourceBinding>(R.layout.activity_open_source) {

    override var logTag: String = "OpenSourceActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_source)

        setSupportActionBar(binding.openSourceToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
