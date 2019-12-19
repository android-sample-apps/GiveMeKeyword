package com.mut_jaeryo.givmkeyword

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.mut_jaeryo.givmkeyword.utills.Database.BasicDB
import com.mut_jaeryo.givmkeyword.utills.Database.FirebaseDB
import com.mut_jaeryo.givmkeyword.utills.Database.ImageSave
import kotlinx.android.synthetic.main.activity_upload.*

class UploadActivity : AppCompatActivity() {

    val Image = ImageSave.drawingImage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //actionBar는 setContent 이전에 호출
        setContentView(R.layout.activity_upload)

        setSupportActionBar(upload_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ImageSave.drawingImage = null
        if(Image != null)
        upload_image.setImageBitmap(Image)

        upload_success.setOnClickListener {

            FirebaseDB.saveDrawing(this,BasicDB.getKeyword(applicationContext)!!,Image!!,BasicDB.getName(applicationContext)!!,upload_content_edit.text.toString())
            finish()
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
