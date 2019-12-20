package com.mut_jaeryo.givmkeyword.view.Adapters

import android.app.Activity

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.mut_jaeryo.givmkeyword.R
import com.mut_jaeryo.givmkeyword.view.Items.drawingItem
import com.mut_jaeryo.givmkeyword.view.ViewHolders.drawingHolder

class DrawingAdapter(var arrayList: ArrayList<drawingItem>,val activity : Activity) : RecyclerView.Adapter<drawingHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): drawingHolder {
        val view  = activity.layoutInflater.inflate(R.layout.drawing_viewer_layout, parent,false)
        return drawingHolder(view)
    }

    //주제가 변경되었을 때, 배열의 내용 변경
    fun changeGoal(arrayList: ArrayList<drawingItem>)
    {
        this.arrayList = arrayList
    }
    override fun getItemCount(): Int  = arrayList.size

    fun setArray(array : ArrayList<drawingItem>)
    {
        this.arrayList = array
    }
    override fun onBindViewHolder(holder: drawingHolder, position: Int) {

        //Image 적용


        val id = arrayList[position].id
        val storageReference = FirebaseStorage.getInstance().reference.child("images/$id.jpg")

        holder.name.text = arrayList[position].name
    // ImageView in your Activity

    // Download directly from StorageReference using Glide
    // (See MyAppGlideModule for Loader registration)
        Glide.with(activity)
                .load(storageReference)
                .into(holder.ImageContainer)
    }
}