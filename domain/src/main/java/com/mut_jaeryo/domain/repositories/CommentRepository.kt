package com.mut_jaeryo.domain.repositories

import androidx.paging.PagingData
import com.mut_jaeryo.domain.entities.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    suspend fun getComments(drawingId: String): Flow<PagingData<Comment>>

    suspend fun createComment(comment: Comment)
}