package com.mut_jaeryo.data.source.drawing.remote

import android.graphics.Bitmap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.firebase.firestore.DocumentSnapshot
import com.mut_jaeryo.data.api.drawing.DrawingService
import com.mut_jaeryo.data.dto.DrawingModel
import com.mut_jaeryo.data.source.drawing.DrawingDataSource
import com.mut_jaeryo.data.source.drawing.DrawingPagingSource
import com.mut_jaeryo.domain.entities.Drawing
import javax.inject.Inject

class RemoteDrawingDataSourceImpl @Inject constructor(
        private val drawingService: DrawingService
) : DrawingDataSource {
    override suspend fun uploadDrawing(drawingModel: DrawingModel) {
        drawingService.uploadDrawing(drawingModel)
    }

    override suspend fun getDrawingListAll(): Pager<DocumentSnapshot, Drawing>? {
        return Pager (
                PagingConfig(pageSize = 25)
        ) {
            DrawingPagingSource(drawingService)
        }
    }

    override suspend fun getDrawingListWithKeyword(keyword: String): Pager<DocumentSnapshot, Drawing>? {
        return Pager (
                PagingConfig(pageSize = 25)
        ) {
            DrawingPagingSource(drawingService, keyword)
        }
    }

    override suspend fun reportDrawing(drawingModel: DrawingModel) {
        drawingService.reportDrawing(drawingModel)
    }

    override suspend fun changeDrawingHeart(drawingModel: DrawingModel) {
        drawingService.changeDrawingHeart(drawingModel)
    }

    override suspend fun getDrawingCachePath(bitmap: Bitmap): String? {
        return null
    }
}