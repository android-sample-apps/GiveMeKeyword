package com.mut_jaeryo.givmkeyword.data.source.drawing.remote

import com.mut_jaeryo.givmkeyword.data.api.drawing.DrawingService
import com.mut_jaeryo.givmkeyword.data.dto.DrawingModel
import com.mut_jaeryo.givmkeyword.data.source.drawing.DrawingDataSource
import javax.inject.Inject

class RemoteDrawingDataSourceImpl @Inject constructor(
        private val drawingService: DrawingService
) : DrawingDataSource {
    override suspend fun uploadDrawing(drawingModel: DrawingModel) {
        drawingService.uploadDrawing(drawingModel)
    }
}