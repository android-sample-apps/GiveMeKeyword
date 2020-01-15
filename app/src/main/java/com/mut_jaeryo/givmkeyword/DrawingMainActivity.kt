package com.mut_jaeryo.givmkeyword

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mut_jaeryo.givmkeyword.utills.AlertUtills
import com.mut_jaeryo.givmkeyword.utills.Database.FirebaseDB
import com.mut_jaeryo.givmkeyword.view.DrawingSNSItems.DoubleClick
import com.mut_jaeryo.givmkeyword.view.DrawingSNSItems.DoubleClickListener
import com.mut_jaeryo.givmkeyword.view.Items.drawingItem
import com.mut_jaeryo.givmkeyword.view.Items.favoriteitem
import com.mut_jaeryo.givmkeyword.view.favoriteAdapter
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.activity_drawing_main.*


class DrawingMainActivity : AppCompatActivity() {

    var item: drawingItem? = null
    var arraylist: ArrayList<favoriteitem>? = null
    lateinit var query : Query
    var canScroll = true
    lateinit var adapter: favoriteAdapter
    val db = FirebaseFirestore.getInstance()

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



        draw_slide_favorite_list.layoutManager = LinearLayoutManager(this)

        darwing_slide_up.addPanelSlideListener(object: SlidingUpPanelLayout.PanelSlideListener{
            override fun onPanelSlide(panel: View?, slideOffset: Float) {

            }

            override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {
                when(newState)
                {
                    SlidingUpPanelLayout.PanelState.EXPANDED ->{
                        if(arraylist==null) {
                            drawing_friend_progress.visibility = View.VISIBLE
                            drawing_friend_progress.spin()

                            arraylist = ArrayList()
                            adapter = favoriteAdapter(arraylist!!, this@DrawingMainActivity)
                            draw_slide_favorite_list.adapter = adapter

                            //돌고있는중
                            query = db.collection(item!!.keyword!!).document(item!!.id!!).collection("hearts")
                                    .limit(25)

                            loadFavorite(false)
                        }
                    }
                }
            }
        })

        draw_slide_favorite_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!draw_slide_favorite_list.canScrollVertically(-1)) {
                    Log.i("list", "Top of list")
                } else if (!draw_slide_favorite_list.canScrollVertically(1)) {
                    Log.i("list", "End of list")
                    if(canScroll)
                    {
                        drawing_slide_refreshLayout.visibility = View.VISIBLE
                        progress_wheel.spin()
                        loadFavorite(true)
                    }
                } else {
                    Log.i("list", "idle")
                }
            }
        })
    }

    private fun loadFavorite(recyclerRefresh : Boolean){
        var moreCheck = 0
        query.get()
                .addOnSuccessListener { querySnapshot ->

                    if(!recyclerRefresh)
                    {
                        Handler().postDelayed({
                            drawing_friend_progress.stopSpinning()
                        },1500)
                    }
                    else
                        EndRefresh()
                    try {
                        val lastVisible = querySnapshot.documents[querySnapshot.size() - 1]
                        query = db.collection(item!!.keyword!!).document(item!!.id!!).collection("hearts")
                                .startAfter(lastVisible)
                                .limit(25)

                        for (document in querySnapshot) {
                            val name: String = document.id
                            arraylist!!.add(favoriteitem(name))
                            moreCheck++
                        }

                        canScroll = !(moreCheck < 25)
                        adapter.notifyDataSetChanged()
                    }catch (e : ArrayIndexOutOfBoundsException)
                    {
                        canScroll = false
                    }
                }.addOnFailureListener {
                    drawing_friend_progress.stopSpinning()
                    AlertUtills.ErrorAlert(applicationContext, "불러오기 실패했습니다 ㅠㅠ")
                    darwing_slide_up.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                }
    }

    private fun EndRefresh()
    {
        Handler().postDelayed({
            progress_wheel.stopSpinning()
            drawing_slide_refreshLayout.visibility = View.GONE
        },1500)

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
