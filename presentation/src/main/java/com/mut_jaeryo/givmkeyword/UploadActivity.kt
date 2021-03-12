package com.mut_jaeryo.givmkeyword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.mut_jaeryo.givmkeyword.utils.Database.BasicDB
import com.mut_jaeryo.givmkeyword.utils.Database.SaveUtils
import kotlinx.android.synthetic.main.activity_upload.*

class UploadActivity : AppCompatActivity() {

    val Image = SaveUtils.drawingImage

    companion object {
        val uploadCode = 300
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //actionBar는 setContent 이전에 호출
        setContentView(R.layout.activity_upload)

        setSupportActionBar(upload_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        upload_name.text = BasicDB.getName(applicationContext)

        if (Image != null)
            upload_image.setImageBitmap(Image)

        upload_success.setOnClickListener {

            val intent = Intent()
            intent.putExtra("content", upload_content_edit.text.toString())
            setResult(uploadCode, intent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
