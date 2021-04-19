package com.mut_jaeryo.presentation.ui.detail

import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.mut_jaeryo.presentation.R
import com.mut_jaeryo.presentation.entities.FavoriteItem
import com.mut_jaeryo.presentation.ui.detail.adapter.DetailAdapter

@BindingAdapter("favoriteIsHeart")
fun setFavoriteIsHeart(view: ImageButton, isHeart: Boolean) {
    if (isHeart) {
        view.setImageResource(R.drawable.ic_favorite)
    } else {
        view.setImageResource(R.drawable.ic_favorite_none)
    }
}

@BindingAdapter("firebaseImage")
fun setFirebaseImage(view: ImageView, id: String) {
    val storageReference = FirebaseStorage.getInstance().reference.child("images/${id}.png")
    Glide.with(view.context)
            .load(storageReference)
            .centerCrop()
            .apply(RequestOptions.bitmapTransform(RoundedCorners(30))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
            ).into(view)
}

@BindingAdapter("favoriteList")
fun setFavoriteList(view: RecyclerView, list: List<FavoriteItem>) {
    val adapter = view.adapter

    if (adapter is DetailAdapter) {
        adapter.submitList(list)
    }
}