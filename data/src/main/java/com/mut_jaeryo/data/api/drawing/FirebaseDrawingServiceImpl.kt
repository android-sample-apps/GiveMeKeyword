package com.mut_jaeryo.data.api.drawing

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.mut_jaeryo.data.R
import com.mut_jaeryo.data.dto.DrawingModel
import com.mut_jaeryo.data.dto.UserModel
import com.mut_jaeryo.data.reponse.DrawingResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class FirebaseDrawingServiceImpl @Inject constructor(
        @ApplicationContext private val context: Context
) : DrawingService {
    @SuppressLint("CheckResult")
    override suspend fun uploadDrawing(drawingModel: DrawingModel) = suspendCancellableCoroutine<Unit> { coroutine ->

        val drawingDocument = FirebaseFirestore.getInstance()
                .collection("images").document()
        val imagesRef = FirebaseStorage.getInstance().reference.child("images/" + drawingDocument.id + ".png")
        val byteStream = ByteArrayOutputStream()
        Log.d("firebase", "이미지 업로드")
        Glide.with(context)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .load(drawingModel.imageUrl)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onLoadFailed(placeholder: Drawable?) {
                        coroutine.cancel(Exception("image load failed"))
                    }

                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        Log.d("firebase", "Bitmap 변환")
                        resource.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
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

                    override fun onLoadCleared(placeholder: Drawable?) {
                        coroutine.cancel()
                    }
                })
    }

    override suspend fun getDrawingAll(lastVisible: DocumentSnapshot?) =
            suspendCancellableCoroutine<DrawingResponse> { coroutine ->
                val db = Firebase.firestore
                val drawingList = mutableListOf<DrawingModel>()

                var query = db.collection("images")
                        .orderBy("hate")

                lastVisible?.let {
                    query = query.startAfter(it)
                }

                query.get()
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

                            coroutine.resume(
                                    DrawingResponse(
                                            data = drawingList,
                                            nextPageNumber = result.documents[result.size() - 1]
                                    )
                            ) {

                            }
                        }
                        .addOnFailureListener { exception ->
                            coroutine.cancel(exception)
                        }
            }

    override suspend fun getDrawingWithKeyword(keyword: String, lastVisible: DocumentSnapshot?) =
            suspendCancellableCoroutine<DrawingResponse> { coroutine ->
                val drawingList = mutableListOf<DrawingModel>()
                val db = Firebase.firestore

                var query = db.collection("images")
                        .whereEqualTo("keyword", keyword)
                        .orderBy("hate")

                lastVisible?.let { query = query.startAfter(it) }

                query.get()
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
                            coroutine.resume(
                                    DrawingResponse(
                                            data = drawingList,
                                            nextPageNumber = result.documents[result.size() - 1]
                                    )
                            ) {

                            }
                        }
                        .addOnFailureListener { exception ->
                            coroutine.cancel(exception)
                        }
            }

    override suspend fun reportDrawing(drawingModel: DrawingModel) = suspendCancellableCoroutine<Unit> { corountine ->
        val db = FirebaseFirestore.getInstance()

        val drawingFireStore = db.collection("images").document(drawingModel.id)

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

    override suspend fun changeDrawingHeart(drawingModel: DrawingModel) = suspendCancellableCoroutine<Unit> { coroutine ->
        val db = FirebaseFirestore.getInstance()

        val drawingFireStore = db.collection("images").document(drawingModel.id)

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