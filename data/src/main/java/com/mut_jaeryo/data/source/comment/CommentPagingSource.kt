package com.mut_jaeryo.data.source.comment

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.DocumentSnapshot
import com.mut_jaeryo.data.api.comment.CommentService
import com.mut_jaeryo.data.mapper.toDomain
import com.mut_jaeryo.domain.entities.Comment

class CommentPagingSource(
        private val commentService: CommentService,
        private val drawingId: String,
        private val userId: String
) : PagingSource<DocumentSnapshot, Comment>() {
    override suspend fun load(
            params: LoadParams<DocumentSnapshot>
    ): LoadResult<DocumentSnapshot, Comment> {
        return try {
            val response =
                commentService.getComments(drawingId, userId, params.key)
           LoadResult.Page(
                    data = response.data.toDomain(),
                    prevKey = null,
                    nextKey = response.nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<DocumentSnapshot, Comment>): DocumentSnapshot? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.nextKey
        }
    }
}