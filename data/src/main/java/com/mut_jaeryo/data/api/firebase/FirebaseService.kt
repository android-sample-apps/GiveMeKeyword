package com.mut_jaeryo.data.api.firebase

import com.mut_jaeryo.data.dto.DrawingModel
import com.mut_jaeryo.data.dto.UserModel
import com.mut_jaeryo.domain.entities.Drawing

interface FirebaseService {
    suspend fun uploadDrawing(drawingModel: DrawingModel)

    suspend fun getDrawingAll(): List<DrawingModel>

    suspend fun getDrawingWithKeyword(keyword: String): List<DrawingModel>

    suspend fun reportDrawing(drawing: Drawing)

    suspend fun changeDrawingHeart(drawing: Drawing)

    suspend fun createUser(userModel: UserModel)
}