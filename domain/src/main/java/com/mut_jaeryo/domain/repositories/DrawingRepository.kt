package com.mut_jaeryo.domain.repositories

import android.graphics.Bitmap
import com.mut_jaeryo.domain.entities.Drawing
import com.mut_jaeryo.domain.entities.Favorite

interface DrawingRepository {
    suspend fun uploadImage(drawing: Drawing)

    suspend fun reportDrawing(drawing: Drawing)

    suspend fun changeDrawingHeart(drawing: Drawing)

    suspend fun getFavoriteList(drawing: Drawing): List<Favorite>

    suspend fun getDrawingListAll(): List<Drawing>

    suspend fun getDrawingCachePath(bitmap: Bitmap): String?

    suspend fun getDrawingListWithKeyword(keyword: String): List<Drawing>
}