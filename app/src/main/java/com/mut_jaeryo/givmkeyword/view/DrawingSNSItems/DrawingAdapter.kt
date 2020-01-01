package com.mut_jaeryo.givmkeyword.view.DrawingSNSItems

import android.app.Activity
import android.app.AlertDialog
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.view.View

import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.mut_jaeryo.givmkeyword.R
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
        val view  = activity.layoutInflater.inflate(R.layout.drawing_viewer_layout, parent,false)
        return drawingHolder(view)
    }

    override fun getItemCount(): Int  = arrayList.size

    override fun onBindViewHolder(holder: drawingHolder, position: Int) {

        //Image 적용

        val item : drawingItem = arrayList[position]

        val id = arrayList[position].id
        val storageReference = FirebaseStorage.getInstance().reference.child("images/$id.jpg")

        holder.more.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
                    .setItems(arrayOf("신고..")) { _, position ->
                        when(position){
                            0-> {
                                //신고
                                FirebaseDB.addHate(arrayList[position],activity.applicationContext)
                            }
                        }
                    }
                    .create()

        }

        holder.ImageContainer.setOnClickListener{

            DoubleClick(object : DoubleClickListener{
                override fun onDoubleClick(view: View?) {
              //      clickListener.onHeartClick(view!!,position)

                    FirebaseDB.changeHeart(arrayList[position],activity.applicationContext)

                    val drawable:Drawable = holder.ImageContainer.drawable
                    holder.heart.alpha = 0.7f

                    when(drawable)
                    {
                        is AnimatedVectorDrawable ->{
                            drawable.start()
                        }

                        is AnimatedVectorDrawableCompat->{
                            drawable.start()
                        }
                    }


                    Toast.makeText(activity,"더블 클릭",Toast.LENGTH_LONG).show()
                }

                override fun onSingleClick(view: View?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
        }
        if(item.heart == 0)
        holder.favorite_text.visibility = View.INVISIBLE
        else
            holder.favorite_text.text = "${item.heart} likes"

        if(item.isHeart)
            holder.favorite_image.setImageResource(R.drawable.favorite)

        holder.content.text = item.content
        holder.name.text = item.name
    // ImageView in your Activity



    // Download directly from StorageReference using Glide
    // (See MyAppGlideModule for Loader registration)
        Glide.with(activity)
                .load(storageReference)
                .into(holder.ImageContainer)
    }
}