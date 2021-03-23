package com.mut_jaeryo.givmkeyword.ui.detail

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mut_jaeryo.givmkeyword.R
import com.mut_jaeryo.givmkeyword.databinding.ActivityDetailBinding
import com.mut_jaeryo.givmkeyword.view.adapter.DoubleClick
import com.mut_jaeryo.givmkeyword.view.adapter.DoubleClickListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.tistory.blackjinbase.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    override var logTag: String = "DrawingMainActivity"

    private val detailViewModel: DetailViewModel by viewModels()

    lateinit var query: Query
    var canScroll = true
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportPostponeEnterTransition()
        setSupportActionBar(binding.drawingMainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initAppBarButton()
        initSlideUpLayout()
        initFavoriteList()
    }

    private fun initAppBarButton() {
        binding.detailKeywordBtn.setOnClickListener {
            it.isSelected = !it.isSelected
            if (it.isSelected) {
                binding.drawingMainGoalLayout.visibility = View.VISIBLE
            } else {
                binding.drawingMainGoalLayout.visibility = View.GONE
            }
        }

        binding.drawingMainMore.setOnClickListener {
            val builder = AlertDialog.Builder(this)
                    .setItems(arrayOf("신고..")) { _, _ ->
                        detailViewModel.reportDrawing()
                    }.create()
            builder.show()
        }

        binding.drawingMainFavorite.setOnClickListener {
            detailViewModel.changeDrawingHeart(false)
        }
    }

    private fun initSlideUpLayout() {
        binding.drawingSlideUp.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {}

            override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {
                when (newState) {
                    SlidingUpPanelLayout.PanelState.EXPANDED -> {
                        binding.drawingFriendProgress.visibility = View.VISIBLE
                        binding.drawingFriendProgress.spin()

//                            //돌고있는중
//                            query = db.collection(item!!.keyword!!).document(item!!.id!!).collection("hearts")
//                                    .limit(25)

                        detailViewModel.loadFavoriteList()
                    }
                }
            }
        })
        binding.drawingMainImage.setOnClickListener(
                DoubleClick(object : DoubleClickListener {
                    override fun onDoubleClick(view: View?) {
                        // 좋아요 상태가 아니라면 변경
                        detailViewModel.changeDrawingHeart(true)

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
    }

    private fun initFavoriteList() {
        binding.drawSlideFavoriteList.apply {
            layoutManager = LinearLayoutManager(applicationContext)
        }
    }

    private fun loadFavorite(recyclerRefresh: Boolean) {
        var moreCheck = 0
//        query.get()
//                .addOnSuccessListener { querySnapshot ->
//                    if (!recyclerRefresh) {
//                        Handler().postDelayed({
//                            binding.drawingFriendProgress.stopSpinning()
//                        }, 1500)
//                    } else
//                     //   endRefresh()
//                    try {
//                        val lastVisible = querySnapshot.documents[querySnapshot.size() - 1]
//                        query = db.collection(item!!.keyword!!).document(item!!.id!!).collection("hearts")
//                                .startAfter(lastVisible)
//                                .limit(25)
//
//                        for (document in querySnapshot) {
//                            val name: String = document.id
//                            arraylist!!.add(Favoriteitem(name))
//                            moreCheck++
//                        }
//
//                        canScroll = moreCheck >= 25
//                        binding.drawSlideFavoriteList.visibility = View.VISIBLE
//                        adapter.notifyDataSetChanged()
//                    } catch (e: ArrayIndexOutOfBoundsException) {
//                        canScroll = false
//                    }
//                }.addOnFailureListener {
//                    binding.drawingFriendProgress.stopSpinning()
//                    AlertUtills.ErrorAlert(applicationContext, "불러오기 실패했습니다 ㅠㅠ")
//                    binding.drawingSlideUp.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
//                }
    }

//    private fun endRefresh() {
//        Handle.postDelayed({
//            progress_wheel.stopSpinning()
//            drawing_slide_refreshLayout.visibility = View.GONE
//        }, 1500)
//    }

    override fun onBackPressed() {
        if (binding.drawingSlideUp.panelState == SlidingUpPanelLayout.PanelState.EXPANDED)
            binding.drawingSlideUp.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
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
