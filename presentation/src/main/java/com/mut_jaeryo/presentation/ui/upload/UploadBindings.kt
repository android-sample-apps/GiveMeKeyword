package com.mut_jaeryo.presentation.ui.upload

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Glide.with(view.context)
                .load(imageUrl)
                .thumbnail(0.25f)
                .into(view)
    }
}