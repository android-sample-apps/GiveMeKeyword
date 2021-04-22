package com.mut_jaeryo.data.api.firebase

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mut_jaeryo.data.R
import com.mut_jaeryo.data.dto.DrawingModel
import com.mut_jaeryo.data.dto.UserModel
import com.mut_jaeryo.domain.entities.Drawing
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class FirebaseDrawingServiceImpl @Inject constructor(
        @ApplicationContext private val context: Context
) : FirebaseService {
    @SuppressLint("CheckResult")
    override suspend fun uploadDrawing(drawingModel: DrawingModel) = suspendCancellableCoroutine<Unit> { coroutine ->

        val drawingDocument = FirebaseFirestore.getInstance()
                .collection("images").document()
        val imagesRef: StorageReference = FirebaseStorage.getInstance().reference.child("images/" + drawingDocument.id + ".png")
        val byteStream = ByteArrayOutputStream()
        Glide.with(context).asBitmap().load(drawingModel.imageUrl)
                .addListener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                        coroutine.cancel(Exception("image load failed"))
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        resource?.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
                        return false
                    }
                })

        val imageByte = byteStream.toByteArray()
        val uploadTask = imagesRef.putBytes(imageByte)
        uploadTask.addOnFailureListener {
            coroutine.cancel(Exception("image load failed"))
        }.addOnSuccessListener {
            val drawingData = hashMapOf(
                    "name" to drawingModel.userName,
                    "content" to drawingModel.content,
                    "heart" to 0,
                    "hate" to 0
            )
            drawingDocument.set(drawingData)
                    .addOnSuccessListener {
                        coroutine.resume(Unit) {

                        }
                    }.addOnCanceledListener {
                        imagesRef.delete()
                        coroutine.cancel()
                    }.addOnFailureListener {
                        imagesRef.delete()
                        coroutine.cancel(Exception("image load failed"))
                    }
        }
    }

    override suspend fun getDrawingAll() =
            suspendCancellableCoroutine<List<DrawingModel>> { coroutine ->
                val db = Firebase.firestore
                val drawingList = mutableListOf<DrawingModel>()

                db.collection("images")
                        .get()
                        .addOnSuccessListener { result ->
                            for (document in result) {
                                drawingList.add(
                                        DrawingModel(
                                                document.id,
                                                document.getString("keyword") ?: "알수없음",
                                                document.getString("name") ?: "알수없음",
                                                content = document.getString("content") ?: "알수없음",
                                                heartCount = document.getLong("heart")?.toInt()
                                                        ?: 0,
                                                isHeart = false
                                        )
                                )
                            }

                            coroutine.resume(drawingList) {

                            }
                        }
                        .addOnFailureListener { exception ->
                            coroutine.cancel(exception)
                        }
            }

    override suspend fun getDrawingWithKeyword(keyword: String) =
            suspendCancellableCoroutine<List<DrawingModel>> { coroutine ->
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
                                                heartCount = document.getLong("heart")?.toInt()
                                                        ?: 0,
                                                isHeart = false))
                            }
                            coroutine.resume(drawingList) {
                                throw it
                            }
                        }
                        .addOnFailureListener { exception ->
                            coroutine.cancel(exception)
                        }
            }

    override suspend fun reportDrawing(drawing: Drawing) = suspendCancellableCoroutine<Unit> { corountine ->
        val db = FirebaseFirestore.getInstance()

        val drawingFireStore = db.collection("images").document(drawing.id)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(drawingFireStore)

            val heartNum = snapshot.getLong("hate")?.toInt() ?: 0 + 1
            transaction.update(drawingFireStore, "heart", heartNum)

            null
        }.addOnSuccessListener {
            corountine.resume(Unit) {

            }
        }.addOnFailureListener { exception ->
            corountine.cancel(exception)
        }

    }

    override suspend fun changeDrawingHeart(drawing: Drawing) = suspendCancellableCoroutine<Unit> { coroutine ->
        val db = FirebaseFirestore.getInstance()

        val drawingFireStore = db.collection("images").document(drawing.id)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(drawingFireStore)

            val heartNum = snapshot.getLong("heart")?.toInt() ?: 0

            transaction.update(drawingFireStore, "heart", heartNum + 1)

            // Success
            null
        }.addOnSuccessListener {
            coroutine.resume(Unit) {

            }
        }.addOnFailureListener { exception ->
            coroutine.cancel(exception)
        }
    }

    override suspend fun createUser(userModel: UserModel) = suspendCancellableCoroutine<Unit> { coroutine ->
        val db = FirebaseFirestore.getInstance().collection("users")
        val doc = db.document(userModel.name) //고유 id를 자동으로 생성

        doc.get().addOnSuccessListener { document ->
            if (document.exists()) {
                coroutine.cancel(Exception(context.getString(R.string.error_duplicated_user_name)))
            } else {
                val data = hashMapOf(
                        "name" to userModel.name,
                        "like" to userModel.like
                )
                doc.set(data)
                        .addOnSuccessListener {
                            coroutine.resume(Unit) {

                            }
                        }
                        .addOnCanceledListener {
                            coroutine.cancel(Exception(context.getString(R.string.error_cancel_create_user)))
                        }
            }
        }.addOnFailureListener { exception ->
            coroutine.cancel(exception)
        }
    }
}