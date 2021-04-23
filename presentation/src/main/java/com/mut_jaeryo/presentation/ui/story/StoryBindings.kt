package com.mut_jaeryo.presentation.ui.story

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.mut_jaeryo.domain.entities.Drawing
import com.mut_jaeryo.presentation.R

@BindingAdapter("storyFirebaseImageWithId")
fun setStoryDrawing(view: ImageView, id: String) {

    val storageReference = FirebaseStorage.getInstance()
            .reference.child("images/$id.png")

    Glide.with(view.context)
            .load(storageReference)
            .thumbnail(0.25f)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(30))
                    .placeholder(R.drawable.bg_shape_square_size)
            )
            .override(300, 500)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
}