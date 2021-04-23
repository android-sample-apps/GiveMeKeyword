package com.mut_jaeryo.data.api.drawing

import com.google.firebase.firestore.DocumentSnapshot
import com.mut_jaeryo.data.dto.DrawingModel
import com.mut_jaeryo.data.dto.UserModel
import com.mut_jaeryo.data.reponse.DrawingResponse

interface DrawingService {
    suspend fun uploadDrawing(drawingModel: DrawingModel)

    suspend fun getDrawingAll(lastVisible: DocumentSnapshot?): DrawingResponse

    suspend fun getDrawingWithKeyword(keyword: String, lastVisible: DocumentSnapshot?): DrawingResponse

    suspend fun reportDrawing(drawingModel: DrawingModel)

    suspend fun changeDrawingHeart(drawingModel: DrawingModel)

    suspend fun createUser(userModel: UserModel)
}