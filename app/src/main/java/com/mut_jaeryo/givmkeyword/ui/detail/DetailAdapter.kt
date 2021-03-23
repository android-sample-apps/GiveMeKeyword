package com.mut_jaeryo.givmkeyword.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mut_jaeryo.givmkeyword.R
import com.mut_jaeryo.givmkeyword.entities.FavoriteItem

import com.mut_jaeryo.givmkeyword.view.holder.favoriteHolder

class DetailAdapter(private val arrayList: List<FavoriteItem>) : RecyclerView.Adapter<favoriteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): favoriteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_person_list, parent, false)
        return favoriteHolder(view)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: favoriteHolder, position: Int) {
        val item: FavoriteItem = arrayList[position]
        holder.name.text = item.name
    }
}