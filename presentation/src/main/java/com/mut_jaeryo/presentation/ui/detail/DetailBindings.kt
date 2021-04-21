package com.mut_jaeryo.presentation.ui.detail

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage

@BindingAdapter("firebaseImage")
fun setFirebaseImage(view: ImageView, id: String) {
    val storageReference = FirebaseStorage.getInstance().reference.child("images/${id}.png")
    Glide.with(view.context)
            .load(storageReference)
            .thumbnail(0.25f)
            .centerCrop()
            .apply(RequestOptions.bitmapTransform(RoundedCorners(30))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
            ).into(view)
}