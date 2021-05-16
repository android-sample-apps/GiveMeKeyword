package com.mut_jaeryo.data.source.comment

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mut_jaeryo.data.api.comment.CommentService
import com.mut_jaeryo.data.mapper.toData
import com.mut_jaeryo.domain.entities.Comment
import com.mut_jaeryo.domain.repositories.CommentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
        private val commentService: CommentService
) : CommentRepository {
    override suspend fun getComments(drawingId: String, userId: String): Flow<PagingData<Comment>> =
            Pager(
                    PagingConfig(pageSize = 25)
            ) {
                CommentPagingSource(commentService, drawingId, userId)
            }.flow

    override suspend fun createComment(comment: Comment) =
            commentService.createComments(comment.toData())

    override suspend fun deleteComment(comment: Comment) =
            commentService.deleteComment(comment.toData())
}