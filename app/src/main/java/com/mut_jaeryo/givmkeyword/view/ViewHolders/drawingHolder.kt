package com.mut_jaeryo.givmkeyword.view.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mut_jaeryo.givmkeyword.R
import kotlinx.android.synthetic.main.drawing_viewer_layout.view.*

class drawingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    public val ImageContainer : ImageView = itemView.findViewById(R.id.drawing_image)
    public val name : TextView = itemView.findViewById(R.id.drawing_name)
    public val content : TextView = itemView.findViewById(R.id.drawing_content)
}