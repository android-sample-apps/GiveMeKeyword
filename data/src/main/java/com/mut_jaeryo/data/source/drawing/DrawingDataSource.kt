package com.mut_jaeryo.data.source.drawing

import com.mut_jaeryo.data.dto.DrawingModel
import com.mut_jaeryo.data.dto.FavoriteModel
import com.mut_jaeryo.domain.entities.Drawing

interface DrawingDataSource {
    suspend fun uploadDrawing(drawingModel: DrawingModel)

    suspend fun getDrawingListAll(): List<DrawingModel>

    suspend fun getDrawingListWithKeyword(keyword: String): List<DrawingModel>

    suspend fun getFavoriteList(drawing: Drawing): List<FavoriteModel>
}