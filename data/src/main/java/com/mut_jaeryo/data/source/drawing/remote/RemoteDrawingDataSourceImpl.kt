package com.mut_jaeryo.data.source.drawing.remote

import android.graphics.Bitmap
import com.mut_jaeryo.data.api.firebase.FirebaseService
import com.mut_jaeryo.data.dto.DrawingModel
import com.mut_jaeryo.data.source.drawing.DrawingDataSource
import com.mut_jaeryo.domain.entities.Drawing
import javax.inject.Inject

class RemoteDrawingDataSourceImpl @Inject constructor(
        private val firebaseService: FirebaseService
) : DrawingDataSource {
    override suspend fun uploadDrawing(drawingModel: DrawingModel) {
        firebaseService.uploadDrawing(drawingModel)
    }

    override suspend fun getDrawingListAll(): List<DrawingModel> {
        return firebaseService.getDrawingAll()
    }

    override suspend fun getDrawingListWithKeyword(keyword: String): List<DrawingModel> {
        return firebaseService.getDrawingWithKeyword(keyword)
    }

    override suspend fun reportDrawing(drawing: Drawing) {
        firebaseService.reportDrawing(drawing)
    }

    override suspend fun changeDrawingHeart(drawing: Drawing) {
        firebaseService.changeDrawingHeart(drawing)
    }

    override suspend fun getDrawingCachePath(bitmap: Bitmap): String? {
        return null
    }
}