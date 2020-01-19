package com.mut_jaeryo.givmkeyword.view.DrawingSNSItems

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent

import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.mut_jaeryo.givmkeyword.DrawingMainActivity
import com.mut_jaeryo.givmkeyword.R
import com.mut_jaeryo.givmkeyword.utills.Database.SaveUtils
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

            val options = ActivityOptions
                    .makeSceneTransitionAnimation(activity,holder.ImageContainer,"draw")

//            if(ImageSave.drawingImage !=null) //사용중인
//            {
//                ImageSave.drawingImage!!.recycle()
//            }

            SaveUtils.drawingImage = holder.ImageContainer.drawable.toBitmap()

            val intent = Intent(activity,DrawingMainActivity::class.java)
//            intent.putExtra("data",item)
            SaveUtils.selectedItem = item
            activity.startActivity(intent,options.toBundle())
        }

        Glide.with(activity.applicationContext)
                .load(storageReference)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(30))
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.shape_sqare_size)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                )
                .override(300,500)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.ImageContainer)

    }
}