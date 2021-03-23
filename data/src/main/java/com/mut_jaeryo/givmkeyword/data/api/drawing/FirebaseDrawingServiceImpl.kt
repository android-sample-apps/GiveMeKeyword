package com.mut_jaeryo.givmkeyword.data.api.drawing

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mut_jaeryo.givmkeyword.data.dto.DrawingModel
import dagger.hilt.android.qualifiers.ActivityContext
import java.io.ByteArrayOutputStream
import java.lang.Exception
import javax.inject.Inject

class FirebaseDrawingServiceImpl @Inject constructor(
     @ActivityContext private val context: Context
) : DrawingService {
    @SuppressLint("CheckResult")
    override suspend fun uploadDrawing(drawingModel: DrawingModel) {
        val drawingDocument = FirebaseFirestore.getInstance()
                .collection(drawingModel.keyword).document()
        val imagesRef: StorageReference = FirebaseStorage.getInstance().reference.child("images/" + drawingDocument.id + ".png")
        val byteStream = ByteArrayOutputStream()
        Glide.with(context).asBitmap().load(drawingModel.imageUrl)
                .addListener(object: RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                        throw Exception("image load failed")
                    }
                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        resource?.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
                        return false
                    }
                })
        val imageByte = byteStream.toByteArray()
        val uploadTask = imagesRef.putBytes(imageByte)
        uploadTask.addOnFailureListener {
            throw Exception("현재 서버문제로 그림 저장에 실패했습니다.")
        }.addOnSuccessListener {
            val drawingData = hashMapOf(
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