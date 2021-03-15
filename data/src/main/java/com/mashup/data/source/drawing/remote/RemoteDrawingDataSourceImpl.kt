package com.mashup.data.source.drawing.remote

import com.mashup.data.api.DrawingService
import com.mashup.data.dto.DrawingModel
import com.mashup.data.source.drawing.DrawingDataSource
import javax.inject.Inject

class RemoteDrawingDataSourceImpl @Inject constructor(
        private val drawingService: DrawingService
) : DrawingDataSource {
    override suspend fun uploadDrawing(drawingModel: DrawingModel) {
        drawingService.uploadDrawing(drawingModel)
    }
}