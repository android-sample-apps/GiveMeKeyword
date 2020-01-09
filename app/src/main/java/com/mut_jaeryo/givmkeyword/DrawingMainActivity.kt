package com.mut_jaeryo.givmkeyword

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.mut_jaeryo.givmkeyword.utills.Database.FirebaseDB
import com.mut_jaeryo.givmkeyword.view.DrawingSNSItems.DoubleClick
import com.mut_jaeryo.givmkeyword.view.DrawingSNSItems.DoubleClickListener
import com.mut_jaeryo.givmkeyword.view.Items.drawingItem
import kotlinx.android.synthetic.main.activity_drawing_main.*


class DrawingMainActivity : AppCompatActivity() {

    var item: drawingItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_main)

        setSupportActionBar(drawing_main_toolbar)

        item = intent.getParcelableExtra("data")

        drawing_main_toolbar.findViewById<ImageButton>(R.id.drawing_main_more).setOnClickListener {

            val builder = AlertDialog.Builder(this)
                    .setItems(arrayOf("신고..")) { _, position ->
                        item.let {
                            when (position) {
                                0 -> {
                                    FirebaseDB.addHate(it!!, applicationContext)
                                }
                            }
                        }
                    }.create()

            builder.show()
        }

        if(item?.isHeart == true)  drawing_main_favorite.setImageResource(R.drawable.favorite)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawing_main_name.text = item?.name ?: ""

        drawing_main_image.setOnClickListener(
                DoubleClick(object : DoubleClickListener {
                    override fun onDoubleClick(view: View?) {
                        //      clickListener.onHeartClick(view!!,position)
                        item?.let { FirebaseDB.changeHeart(it, applicationContext) }

                        val drawable: Drawable = drawing_main_like_imageView.drawable
                        drawing_main_like_imageView.alpha = 0.7f

                        when (drawable) {
                            is AnimatedVectorDrawable -> {
                                drawable.start()
                            }

                            is AnimatedVectorDrawableCompat -> {
                                drawable.start()
                            }
                        }

                        drawing_main_favorite.setImageResource(R.drawable.favorite)

                    }

                    override fun onSingleClick(view: View?) {

                    }

                }))

        drawing_main_content.text =  item?.content ?: ""
        drawing_slide_favorite_count.text = "좋아하는 사람 (${item!!.heart})"
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
