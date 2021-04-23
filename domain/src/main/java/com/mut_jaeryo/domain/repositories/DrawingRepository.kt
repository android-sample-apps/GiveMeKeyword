package com.mut_jaeryo.domain.repositories

import android.graphics.Bitmap
import androidx.paging.PagingData
import com.mut_jaeryo.domain.entities.Drawing
import kotlinx.coroutines.flow.Flow

interface DrawingRepository {
    suspend fun uploadImage(drawing: Drawing)

    suspend fun reportDrawing(drawing: Drawing)

    suspend fun changeDrawingHeart(drawing: Drawing)

    suspend fun getDrawingListAll(): Flow<PagingData<Drawing>>

    suspend fun getDrawingCachePath(bitmap: Bitmap): String?

    suspend fun getDrawingListWithKeyword(keyword: String): Flow<PagingData<Drawing>>
}