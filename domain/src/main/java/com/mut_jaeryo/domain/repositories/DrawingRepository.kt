package com.mut_jaeryo.domain.repositories

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.mut_jaeryo.domain.entities.Drawing

interface DrawingRepository {
    suspend fun uploadImage(drawing: Drawing)

    suspend fun reportDrawing(drawing: Drawing)

    suspend fun changeDrawingHeart(drawing: Drawing)

    suspend fun getDrawingListAll(): LiveData<PagingData<Drawing>>

    suspend fun getDrawingCachePath(bitmap: Bitmap): String?

    suspend fun getDrawingListWithKeyword(keyword: String): LiveData<PagingData<Drawing>>
}