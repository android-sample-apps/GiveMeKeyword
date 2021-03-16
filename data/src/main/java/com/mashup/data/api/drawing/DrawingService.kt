package com.mashup.data.api.drawing

import com.mashup.data.dto.DrawingModel

interface DrawingService {
    suspend fun uploadDrawing(drawingModel: DrawingModel)
}