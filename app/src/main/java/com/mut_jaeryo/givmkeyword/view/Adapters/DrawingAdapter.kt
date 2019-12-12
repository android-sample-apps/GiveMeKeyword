package com.mut_jaeryo.givmkeyword.view.Adapters

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    override fun onBindViewHolder(holder: drawingHolder, position: Int) {

        //Image 적용
    }
}