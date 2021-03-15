package com.mashup.data.source.drawing

import com.mashup.data.mapper.toData
import com.mashup.domain.entities.Drawing
import com.mashup.domain.repositories.DrawingRepository
import javax.inject.Inject

class DrawingRepositoryImpl @Inject constructor(
        private val remoteDrawingDataSource: DrawingDataSource
) : DrawingRepository {
    override suspend fun uploadImage(drawing: Drawing) {
        remoteDrawingDataSource.uploadDrawing(drawing.toData())
    }
}