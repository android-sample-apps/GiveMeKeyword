package com.mut_jaeryo.givmkeyword.view.ViewHolders

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mut_jaeryo.givmkeyword.R
import kotlinx.android.synthetic.main.drawing_viewer_layout.view.*

class drawingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    public val ImageContainer : ImageView = itemView.findViewById(R.id.image)
}