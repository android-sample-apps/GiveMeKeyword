package com.mut_jaeryo.data.source.drawing

import android.graphics.Bitmap
import com.mut_jaeryo.data.dto.DrawingModel
import com.mut_jaeryo.data.dto.FavoriteModel
import com.mut_jaeryo.domain.entities.Drawing

interface DrawingDataSource {
    suspend fun uploadDrawing(drawingModel: DrawingModel)

    suspend fun getDrawingListAll(): List<DrawingModel>

    suspend fun getDrawingListWithKeyword(keyword: String): List<DrawingModel>

    suspend fun reportDrawing(drawing: Drawing)

    suspend fun changeDrawingHeart(drawing: Drawing)

    suspend fun getDrawingCachePath(bitmap: Bitmap): String?
}