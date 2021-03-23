package com.mut_jaeryo.presentation.entities

import java.io.Serializable

data class DrawingItem(val id: String, val keyword: String, val name: String, val content: String,
                       val heartCount: Int, val isHeart: Boolean) : Serializable