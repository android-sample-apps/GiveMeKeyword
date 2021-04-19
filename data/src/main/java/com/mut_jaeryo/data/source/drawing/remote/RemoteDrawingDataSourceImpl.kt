package com.mut_jaeryo.data.source.drawing.remote

import com.mut_jaeryo.data.api.drawing.DrawingService
import com.mut_jaeryo.data.dto.DrawingModel
import com.mut_jaeryo.data.dto.FavoriteModel
import com.mut_jaeryo.data.source.drawing.DrawingDataSource
import com.mut_jaeryo.domain.entities.Drawing
import javax.inject.Inject

class RemoteDrawingDataSourceImpl @Inject constructor(
        private val drawingService: DrawingService
) : DrawingDataSource {
    override suspend fun uploadDrawing(drawingModel: DrawingModel) {
        drawingService.uploadDrawing(drawingModel)
    }

    override suspend fun getDrawingListAll(): List<DrawingModel> {
        return drawingService.getDrawingAll()
    }

    override suspend fun getDrawingListWithKeyword(keyword: String): List<DrawingModel> {
        return drawingService.getDrawingWithKeyword(keyword)
    }

    override suspend fun getFavoriteList(drawing: Drawing): List<FavoriteModel> {
        return drawingService.getFavoriteList(drawing)
    }
}