package com.mashup.data.source.drawing

import com.mashup.data.dto.DrawingModel

interface DrawingDataSource {
    suspend fun uploadDrawing(drawingModel: DrawingModel)
}