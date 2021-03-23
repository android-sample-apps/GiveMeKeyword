package com.mut_jaeryo.givmkeyword.domain.repositories

import com.mut_jaeryo.givmkeyword.domain.entities.Drawing
import com.mut_jaeryo.givmkeyword.domain.entities.Favorite

interface DrawingRepository {
    suspend fun uploadImage(drawing: Drawing)

    suspend fun reportDrawing(drawing: Drawing)

    suspend fun changeDrawingHeart(drawing: Drawing)

    suspend fun getFavoriteList(drawing: Drawing): List<Favorite>
}