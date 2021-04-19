package com.mut_jaeryo.data.api.drawing

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mut_jaeryo.data.dto.DrawingModel
import com.mut_jaeryo.data.dto.FavoriteModel
import com.mut_jaeryo.domain.entities.Drawing
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import java.lang.Exception
import javax.inject.Inject

class FirebaseDrawingServiceImpl @Inject constructor(
     @ApplicationContext private val context: Context
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
            throw Exception("image load failed")
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
                        throw Exception("image load failed")
                    }
        }
    }

    override suspend fun getDrawingAll(): List<DrawingModel> {
        val drawingList = mutableListOf<DrawingModel>()
        val db = Firebase.firestore

        db.collection("images")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        drawingList.add(
                                DrawingModel(document.id,
                                        document.getString("keyword") ?: "알수없음",
                                        document.getString("name") ?: "알수없음",
                                        content = document.getString("content") ?: "알수없음",
                                        heartCount = document.getLong("heart")?.toInt() ?: 0,
                                        isHeart = false))
                    }
                }
                .addOnFailureListener { exception ->
                    throw exception
                }
        return drawingList
    }

    override suspend fun getDrawingWithKeyword(keyword: String): List<DrawingModel> {
        val drawingList = mutableListOf<DrawingModel>()
        val db = Firebase.firestore

        db.collection("images")
                .whereEqualTo("keyword", keyword)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        drawingList.add(
                                DrawingModel(document.id,
                                        document.getString("keyword") ?: "알수없음",
                                        document.getString("name") ?: "알수없음",
                                        content = document.getString("content") ?: "알수없음",
                                        heartCount = document.getLong("heart")?.toInt() ?: 0,
                                        isHeart = false))
                    }
                }
                .addOnFailureListener { exception ->
                    throw exception
                }
        return drawingList
    }

    override suspend fun getFavoriteList(drawing: Drawing): List<FavoriteModel> {
        val favoriteList = mutableListOf<FavoriteModel>()
        val db = Firebase.firestore

        db.collection("images").document(drawing.id).collection("hearts")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        favoriteList.add(FavoriteModel(document.id))
                    }
                }
                .addOnFailureListener { exception ->
                    throw exception
                }
        return favoriteList
    }

    override suspend fun reportDrawing(drawing: Drawing) {
        val db = FirebaseFirestore.getInstance()

        val drawingFireStore = db.collection("images").document(drawing.id)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(drawingFireStore)

            val heartNum = snapshot.getLong("hate")?.toInt() ?: 0 + 1
            transaction.update(drawingFireStore, "heart", heartNum)

            null
        }.addOnSuccessListener {

        }.addOnFailureListener { exception ->
           throw exception
        }

    }

    override suspend fun changeDrawingHeart(drawing: Drawing) {
        val db = FirebaseFirestore.getInstance()

        val drawingFireStore = db.collection("images").document(drawing.id)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(drawingFireStore)

            val heartNum = snapshot.getLong("heart")?.toInt() ?: 0 + 1

            transaction.update(drawingFireStore, "heart", heartNum)

            // Success
            null
        }.addOnSuccessListener {

        }.addOnFailureListener { exception ->
           throw exception
        }
    }
}