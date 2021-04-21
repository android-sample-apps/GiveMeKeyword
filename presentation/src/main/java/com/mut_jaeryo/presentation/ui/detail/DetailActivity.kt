package com.mut_jaeryo.presentation.ui.detail

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.mut_jaeryo.presentation.R
import com.mut_jaeryo.presentation.databinding.ActivityDetailBinding
import com.tistory.blackjinbase.base.BaseActivity
import com.tistory.blackjinbase.ext.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    override var logTag: String = "DrawingMainActivity"
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.drawingMainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.viewModel = detailViewModel

        initAppBarButton()
        initDrawingImage()

        observeViewModel()
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
                    .setItems(arrayOf(getString(R.string.detail_report))) { _, _ ->
                        detailViewModel.reportDrawing()
                    }.create()
            builder.show()
        }

    }

    private fun initDrawingImage() {
        binding.drawingMainImage.setOnClickListener(
                DoubleClick(object : DoubleClick.DoubleClickListener {
                    override fun onDoubleClick(view: View?) {
                        detailViewModel.changeDrawingHeart()

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
                    }
                    override fun onSingleClick(view: View?) {
                    }
                })
        )
    }

    private fun observeViewModel() {
        detailViewModel.errorMessage.observe(this) {
            toast(it)
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
