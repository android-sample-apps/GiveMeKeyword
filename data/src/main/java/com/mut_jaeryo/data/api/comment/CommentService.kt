package com.mut_jaeryo.data.api.comment

import com.google.firebase.firestore.DocumentSnapshot
import com.mut_jaeryo.data.dto.CommentModel
import com.mut_jaeryo.data.reponse.CommentResponse

interface CommentService {
    suspend fun getComments(
            drawingId: String,
            userId: String,
            lastVisible: DocumentSnapshot?
    ): CommentResponse

    suspend fun createComments(commentModel: CommentModel)

    suspend fun deleteComment(commentModel: CommentModel)
}