package com.mut_jaeryo.givmkeyword.view.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mut_jaeryo.givmkeyword.R


class favoriteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    public val ImageContainer: ImageView = itemView.findViewById(R.id.favorite_profile)
    public val name: TextView = itemView.findViewById(R.id.favorite_name)
//    public val content : TextView = itemView.findViewById(R.id.drawing_content)
//    public val heart : ImageView = itemView.findViewById(R.id.like_imageView)
//    public val more: ImageButton = itemView.findViewById(R.id.drawing_more)
//    val favorite_image : ImageView = itemView.findViewById(R.id.favorite_imageView)
//    val favorite_text : TextView =  itemView.findViewById(R.id.like_textView)
}