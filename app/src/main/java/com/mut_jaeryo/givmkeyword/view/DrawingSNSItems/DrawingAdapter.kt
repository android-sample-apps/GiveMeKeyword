package com.mut_jaeryo.givmkeyword.view.DrawingSNSItems

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View

import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.mut_jaeryo.givmkeyword.DrawingMainActivity
import com.mut_jaeryo.givmkeyword.R
import com.mut_jaeryo.givmkeyword.utills.AlertUtills
import com.mut_jaeryo.givmkeyword.utills.Database.FirebaseDB
import com.mut_jaeryo.givmkeyword.view.Items.drawingItem
import com.mut_jaeryo.givmkeyword.view.ViewHolders.drawingHolder


class DrawingAdapter(var arrayList: ArrayList<drawingItem>,val activity : Activity) : RecyclerView.Adapter<drawingHolder>() {



/*
    interface ClickEvent
    {
        fun onHeartClick(view: View,position: Int)
    }

    lateinit var clickListener : ClickEvent

    fun setClickEvent(listener:ClickEvent){
        clickListener = listener
    }

 */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): drawingHolder {
        val view  = activity.layoutInflater.inflate(R.layout.drawing_list_layout, parent,false)
        return drawingHolder(view)
    }


    fun changeArray(array:ArrayList<drawingItem>)
    {
        arrayList = array
    }
    override fun getItemCount(): Int  = arrayList.size

    override fun onBindViewHolder(holder: drawingHolder, position: Int) {

        //Image 적용

        val item : drawingItem = arrayList[position]

        val id = arrayList[position].id
        val storageReference = FirebaseStorage.getInstance().reference.child("images/$id.png")

        holder.ImageContainer.setOnClickListener{
            val intent = Intent(activity,DrawingMainActivity::class.java)
            intent.putExtra("data",item)
            activity.startActivity(intent)
        }

        Glide.with(activity.applicationContext)
                .load(storageReference)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(30))
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                )
                .into(holder.ImageContainer)

    }
}