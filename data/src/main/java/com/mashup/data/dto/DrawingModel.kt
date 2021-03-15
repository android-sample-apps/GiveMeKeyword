package com.mashup.data.dto

import android.graphics.Bitmap

data class DrawingModel(
        val keyword: String,
        val userName: String,
        val drawing: Bitmap,
        val content: String,
        )
