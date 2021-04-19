package com.mut_jaeryo.data.api.drawing

import com.mut_jaeryo.data.dto.DrawingModel
import com.mut_jaeryo.data.dto.FavoriteModel
import com.mut_jaeryo.domain.entities.Drawing

interface DrawingService {
    suspend fun uploadDrawing(drawingModel: DrawingModel)

    suspend fun getDrawingAll(): List<DrawingModel>

    suspend fun getDrawingWithKeyword(keyword: String): List<DrawingModel>

    suspend fun getFavoriteList(drawing: Drawing): List<FavoriteModel>

    suspend fun reportDrawing(drawing: Drawing)

    suspend fun changeDrawingHeart(drawing: Drawing)
}