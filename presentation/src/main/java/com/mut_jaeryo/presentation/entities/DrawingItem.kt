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

    companion object {
        val DiffUtilCallback =  object : DiffUtil.ItemCallback<DrawingItem>() {
            override fun areItemsTheSame(oldItem: DrawingItem, newItem: DrawingItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DrawingItem, newItem: DrawingItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}