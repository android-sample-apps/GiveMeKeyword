package com.mut_jaeryo.presentation.entities

import androidx.recyclerview.widget.DiffUtil
import java.io.Serializable

data class DrawingItem(
        val id: String = "0",
        val keyword: String,
        val name: String,
        val content: String,
        val heartCount: Int = 0,
        val isHeart: Boolean = false) : Serializable {
}