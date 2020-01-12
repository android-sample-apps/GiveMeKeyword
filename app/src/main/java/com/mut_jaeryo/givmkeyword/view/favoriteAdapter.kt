package com.mut_jaeryo.givmkeyword.view

import android.app.Activity
import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mut_jaeryo.givmkeyword.DrawingMainActivity
import com.mut_jaeryo.givmkeyword.R

import com.mut_jaeryo.givmkeyword.view.Items.favoriteitem
import com.mut_jaeryo.givmkeyword.view.ViewHolders.favoriteHolder

class favoriteAdapter(var arrayList: ArrayList<favoriteitem>, val activity : Activity) : RecyclerView.Adapter<favoriteHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): favoriteHolder {
        val view  = activity.layoutInflater.inflate(R.layout.drawing_list_layout, parent,false)
        return favoriteHolder(view)
    }

    override fun getItemCount(): Int  = arrayList.size

    override fun onBindViewHolder(holder: favoriteHolder, position: Int) {

        //Image 적용

        val item : favoriteitem = arrayList[position]


        holder.name.text =  item.name

    }
}