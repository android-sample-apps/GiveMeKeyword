package com.mut_jaeryo.givmkeyword.ui.detail

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.mut_jaeryo.givmkeyword.R
import com.mut_jaeryo.givmkeyword.databinding.ActivityDetailBinding
import com.mut_jaeryo.givmkeyword.utils.AlertUtills
import com.mut_jaeryo.givmkeyword.utils.database.FirebaseDB
import com.mut_jaeryo.givmkeyword.view.DrawingSNSItems.DoubleClick
import com.mut_jaeryo.givmkeyword.view.DrawingSNSItems.DoubleClickListener
import com.mut_jaeryo.givmkeyword.view.Items.drawingItem
import com.mut_jaeryo.givmkeyword.view.Items.favoriteitem
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.tistory.blackjinbase.base.BaseActivity

class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    override var logTag: String = "DrawingMainActivity"

    private val detailViewModel: DetailViewModel by viewModels()

    var item: drawingItem? = null
    var arraylist: ArrayList<favoriteitem>? = null
    lateinit var query: Query
    var canScroll = true
    lateinit var adapter: FavoriteAdapter
    val db = FirebaseFirestore.getInstance()
    var myitem: favoriteitem? = null

    override fun onEnterAnimationComplete() {
        binding.drawingMainName.text = item?.name ?: ""
        binding.drawingMainImage.setOnClickListener(
                DoubleClick(object : DoubleClickListener {
                    override fun onDoubleClick(view: View?) {
                        // 좋아요 상태가 아니라면 변경
                        if (!item!!.isHeart)
                            FirebaseDB.changeHeart(item!!, applicationContext)

                        val drawable: Drawable = binding.drawingMainLikeImageView.drawable
                        binding.drawingMainLikeImageView.alpha = 0.7f

                        when (drawable) {
                            is AnimatedVectorDrawable -> {
                                drawable.start()
                            }

                            is AnimatedVectorDrawableCompat -> {
                                drawable.start()
                            }
                        }
                        binding.drawingMainFavorite.setImageResource(R.drawable.favorite)
                    }

                    override fun onSingleClick(view: View?) {

                    }
                }))

        binding.drawingMainContent.text = item?.content ?: ""
        binding.drawingSlideFavoriteCount.text = "좋아하는 사람 (${item!!.heart})"


        binding.drawSlideFavoriteList.layoutManager = LinearLayoutManager(this)

        binding.darwingSlideUp.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {

            }

            override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {
                when (newState) {
                    SlidingUpPanelLayout.PanelState.EXPANDED -> {
                        if (arraylist!!.size == 0) {
                            binding.drawingFriendProgress.visibility = View.VISIBLE
                            binding.drawingFriendProgress.spin()

                            arraylist = ArrayList()
                            adapter = FavoriteAdapter(arraylist!!, this@DetailActivity)
                            binding.drawSlideFavoriteList.adapter = adapter

                            //돌고있는중
                            query = db.collection(item!!.keyword!!).document(item!!.id!!).collection("hearts")
                                    .limit(25)

                            loadFavorite(false)
                        } else {
                            adapter.notifyDataSetChanged()

                        }
                    }
                }
            }
        })

        binding.drawSlideFavoriteList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!binding.drawSlideFavoriteList.canScrollVertically(-1)) {
                } else if (!binding.drawSlideFavoriteList.canScrollVertically(1)) {
                    if (canScroll) {
                        binding.drawingSlideRefreshLayout.visibility = View.VISIBLE
                        binding.progressWheel.spin()
                        loadFavorite(true)
                    }
                } else {
                    Log.i("list", "idle")
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        arraylist = ArrayList()
        supportPostponeEnterTransition()
        setSupportActionBar(binding.drawingMainToolbar)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        item = SaveUtils.selectedItem

        drawing_main_image.setImageBitmap(SaveUtils.drawingImage)

        util_realGoal.text = item!!.keyword

        drawing_main_keyword.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                drawing_main_goal_layout.visibility = View.VISIBLE

            } else if (motionEvent.action == MotionEvent.ACTION_UP) {
                drawing_main_goal_layout.visibility = View.GONE
            }
            false
        }

        val storageReference = FirebaseStorage.getInstance().reference.child("images/${item!!.id}.png")
        Glide.with(this)
                .load(storageReference)
                .centerCrop()
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }
                })
                .apply(RequestOptions.bitmapTransform(RoundedCorners(30))
                        .skipMemoryCache(true)
                        .placeholder(drawing_main_image.drawable)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                )
                .into(drawing_main_image)





        drawing_main_favorite.setOnClickListener {
            if (!item!!.isHeart) //좋아요 안한 상태
            {
                drawing_main_favorite.setImageResource(R.drawable.favorite)
                item!!.heart++
                drawing_slide_favorite_count.text = "좋아하는 사람 (${item!!.heart})"
                myitem = favoriteitem(Preference.getName(applicationContext))
                arraylist!!.add(myitem!!)
            } else //좋아요 되어있는 상태
            {
                item!!.heart--
                drawing_main_favorite.setImageResource(R.drawable.favorite_none)
                drawing_slide_favorite_count.text = "좋아하는 사람 (${item!!.heart})"
                myitem.let {
                    arraylist!!.remove(it)
                }

            }
            FirebaseDB.changeHeart(item!!, applicationContext)
        }

        if (item!!.isHeart) drawing_main_favorite.setImageResource(R.drawable.favorite)
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
    }

    private fun loadFavorite(recyclerRefresh: Boolean) {
        var moreCheck = 0
        query.get()
                .addOnSuccessListener { querySnapshot ->

                    if (!recyclerRefresh) {
                        Handler().postDelayed({
                            binding.drawingFriendProgress.stopSpinning()
                        }, 1500)
                    } else
                     //   endRefresh()
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

                        canScroll = moreCheck >= 25
                        binding.drawSlideFavoriteList.visibility = View.VISIBLE
                        adapter.notifyDataSetChanged()
                    } catch (e: ArrayIndexOutOfBoundsException) {
                        canScroll = false
                    }
                }.addOnFailureListener {
                    binding.drawingFriendProgress.stopSpinning()
                    AlertUtills.ErrorAlert(applicationContext, "불러오기 실패했습니다 ㅠㅠ")
                    binding.darwingSlideUp.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                }
    }

//    private fun endRefresh() {
//        Handle.postDelayed({
//            progress_wheel.stopSpinning()
//            drawing_slide_refreshLayout.visibility = View.GONE
//        }, 1500)
//    }

    override fun onBackPressed() {
        if (binding.darwingSlideUp.panelState == SlidingUpPanelLayout.PanelState.EXPANDED)
            binding.darwingSlideUp.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        else
            finish()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finishAfterTransition()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
