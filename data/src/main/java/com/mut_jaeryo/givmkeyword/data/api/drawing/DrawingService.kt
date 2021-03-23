package com.mut_jaeryo.givmkeyword.data.api.drawing

import com.mut_jaeryo.givmkeyword.data.dto.DrawingModel

interface DrawingService {
    suspend fun uploadDrawing(drawingModel: DrawingModel)
}