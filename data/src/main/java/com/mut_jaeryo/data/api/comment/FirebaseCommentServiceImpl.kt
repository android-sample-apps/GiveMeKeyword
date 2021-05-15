package com.mut_jaeryo.data.api.comment

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mut_jaeryo.data.dto.CommentModel
import com.mut_jaeryo.data.reponse.CommentResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import javax.inject.Inject

class FirebaseCommentServiceImpl @Inject constructor(
) : CommentService {
    override suspend fun getComments(
            drawingId: String,
            lastVisible: DocumentSnapshot?
    ): CommentResponse =
            suspendCancellableCoroutine { coroutine ->
                val db = Firebase.firestore
                val commentList = mutableListOf<CommentModel>()

                var query = db.collection("comments")
                        .whereEqualTo("drawingId", drawingId)
                        .orderBy("createdAt")

                lastVisible?.let {
                    query = query.startAfter(it)
                }

                query.limit(25)
                        .get()
                        .addOnSuccessListener { result ->
                            for (document in result) {
                                commentList.add(
                                        CommentModel(
                                                id = document.id,
                                                userId = document.getString("userId") ?: "알수없음",
                                                comment = document.getString("comment") ?: "알수 없음",
                                                drawingId = document.getString("documentId") ?: "알수없음"
                                        )
                                )
                            }

                            coroutine.resume(
                                    CommentResponse(
                                            data = commentList,
                                            nextPageNumber = if (result.size() > 0) result.documents[result.size() - 1] else null
                                    )
                            ) {

                            }
                        }
                        .addOnFailureListener { exception ->
                            coroutine.cancel(exception)
                        }
            }

    override suspend fun createComments(
            commentModel: CommentModel
    ) = suspendCancellableCoroutine<Unit> { coroutine ->
        val db = FirebaseFirestore.getInstance().collection("comments")
        val doc = db.document()
        val data = hashMapOf(
                "drawingId" to commentModel.drawingId,
                "userId" to commentModel.userId,
                "comment" to commentModel.comment,
                "createdAt" to Timestamp(Date())
        )

        doc.set(data).addOnSuccessListener {
            coroutine.resume(Unit) {

            }
        }.addOnFailureListener { exception ->
            coroutine.cancel(exception)
        }
    }

    override suspend fun deleteComment(commentModel: CommentModel) =
            suspendCancellableCoroutine<Unit> { coroutine ->
                val db = FirebaseFirestore.getInstance().collection("comments")
                val doc = db.document(commentModel.id)

                doc.delete().addOnSuccessListener {
                    coroutine.resume(Unit) {

                    }
                }.addOnFailureListener { exception ->
                    coroutine.cancel(exception)
                }
            }
}