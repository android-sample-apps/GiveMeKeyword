package com.mut_jaeryo.presentation.ui.drawing

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mut_jaeryo.presentation.custom.InkView

@BindingAdapter("backgroundColor")
fun setBackgroundColor(view: ImageView, color: Int) {
    view.setBackgroundColor(color)
}

@BindingAdapter("brushColor")
fun setBrushColor(view: InkView, color: Int) {
    view.setColor(color)
}