package com.mut_jaeryo.givmkeyword.data.source.drawing

import com.mut_jaeryo.givmkeyword.data.dto.DrawingModel

interface DrawingDataSource {
    suspend fun uploadDrawing(drawingModel: DrawingModel)
}