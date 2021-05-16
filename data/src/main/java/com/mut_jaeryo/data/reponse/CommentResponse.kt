package com.mut_jaeryo.data.reponse

import com.google.firebase.firestore.DocumentSnapshot
import com.mut_jaeryo.data.dto.CommentModel
import com.mut_jaeryo.data.dto.DrawingModel

data class CommentResponse(val data: List<CommentModel>, val nextPageNumber: DocumentSnapshot?)
