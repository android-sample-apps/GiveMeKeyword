package com.mut_jaeryo.givmkeyword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.mut_jaeryo.givmkeyword.utills.Database.BasicDB
import com.mut_jaeryo.givmkeyword.utills.Database.FirebaseDB
import com.mut_jaeryo.givmkeyword.utills.Database.SaveUtils
import kotlinx.android.synthetic.main.activity_upload.*

class UploadActivity : AppCompatActivity() {

    val Image = SaveUtils.drawingImage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //actionBar는 setContent 이전에 호출
        setContentView(R.layout.activity_upload)

        setSupportActionBar(upload_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        upload_name.text = BasicDB.getName(applicationContext)
        SaveUtils.drawingImage = null
        if(Image != null)
        upload_image.setImageBitmap(Image)

        upload_success.setOnClickListener {

            FirebaseDB.saveDrawing(this,BasicDB.getKeyword(applicationContext)!!,Image!!,BasicDB.getName(applicationContext)!!,upload_content_edit.text.toString())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
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
