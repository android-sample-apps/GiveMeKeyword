package com.mut_jaeryo.givmkeyword.ui.drawing

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mut_jaeryo.givmkeyword.view.InkView

@BindingAdapter("backgroundColor")
fun setBackgroundColor(view: ImageView, color: Int) {
    view.setBackgroundColor(color)
}

@BindingAdapter("brushColor")
fun setBrushColor(view: InkView, color: Int) {
    view.setColor(color)
}