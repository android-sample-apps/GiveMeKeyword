package com.mut_jaeryo.data.reponse

import com.google.firebase.firestore.DocumentSnapshot
import com.mut_jaeryo.data.dto.DrawingModel

data class DrawingResponse(val data: List<DrawingModel>, val nextPageNumber: DocumentSnapshot)
