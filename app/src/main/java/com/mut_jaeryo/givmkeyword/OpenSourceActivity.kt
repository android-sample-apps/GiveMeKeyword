package com.mut_jaeryo.givmkeyword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_open_source.*

class OpenSourceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_source)

        setSupportActionBar(open_source_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}
