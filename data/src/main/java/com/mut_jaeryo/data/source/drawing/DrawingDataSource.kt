package com.mut_jaeryo.data.source.drawing

import android.graphics.Bitmap
import androidx.paging.Pager
import com.google.firebase.firestore.DocumentSnapshot
import com.mut_jaeryo.data.dto.DrawingModel
import com.mut_jaeryo.data.dto.FavoriteModel
import com.mut_jaeryo.domain.entities.Drawing

interface DrawingDataSource {
    suspend fun uploadDrawing(drawingModel: DrawingModel)

    suspend fun getDrawingListAll(): Pager<DocumentSnapshot, Drawing>?

    suspend fun getDrawingListWithKeyword(keyword: String): Pager<DocumentSnapshot, Drawing>?

    suspend fun reportDrawing(drawingModel: DrawingModel)

    suspend fun changeDrawingHeart(drawingModel: DrawingModel)

    suspend fun getDrawingCachePath(bitmap: Bitmap): String?
}