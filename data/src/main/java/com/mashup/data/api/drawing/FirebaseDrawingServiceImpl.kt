package com.mashup.data.api.drawing

import android.graphics.Bitmap
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mashup.data.dto.DrawingModel
import java.io.ByteArrayOutputStream
import java.lang.Exception

object FirebaseDrawingServiceImpl : DrawingService {
    override suspend fun uploadDrawing(drawingModel: DrawingModel) {
        val drawingDocument = FirebaseFirestore.getInstance()
                .collection(drawingModel.keyword).document()
        val imagesRef: StorageReference = FirebaseStorage.getInstance().reference.child("images/" + drawingDocument.id + ".png")
        val byteStream = ByteArrayOutputStream()
        drawingModel.drawing.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
        val imageByte = byteStream.toByteArray()
        val uploadTask = imagesRef.putBytes(imageByte)
        uploadTask.addOnFailureListener {
            throw Exception("현재 서버문제로 그림 저장에 실패했습니다.")
        }.addOnSuccessListener {
            var drawingData = hashMapOf(
                    "name" to drawingModel.userName,
                    "content" to drawingModel.content,
                    "heart" to 0,
                    "hate" to 0
            )
            drawingDocument.set(drawingData)
                    .addOnSuccessListener {

                    }.addOnCanceledListener {
                        imagesRef.delete()
                    }.addOnFailureListener {
                        imagesRef.delete()
                        throw Exception("현재 서버문제로 그림 저장에 실패했습니다.")
                    }
        }
    }
}