package com.mashup.domain.repositories

import com.mashup.domain.entities.Drawing
import com.mashup.domain.entities.Favorite

interface DrawingRepository {
    suspend fun uploadImage(drawing: Drawing)

    suspend fun reportDrawing(drawing: Drawing)

    suspend fun changeDrawingHeart(drawing: Drawing)

    suspend fun getFavoriteList(drawing: Drawing): List<Favorite>
}