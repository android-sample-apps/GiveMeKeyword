package com.mut_jaeryo.data.source.drawing

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.DocumentSnapshot
import com.mut_jaeryo.data.api.drawing.DrawingService
import com.mut_jaeryo.data.dto.DrawingModel
import com.mut_jaeryo.data.mapper.toDomain
import com.mut_jaeryo.domain.entities.Drawing

class DrawingPagingSource(
        private val drawingService: DrawingService,
        private val keyword: String? = null
) : PagingSource<DocumentSnapshot, Drawing>() {
    override suspend fun load(
            params: LoadParams<DocumentSnapshot>
    ): LoadResult<DocumentSnapshot, Drawing> {
        Log.d("drawingPaging", "pagingLoad")
        return try {
            val response = if (keyword == null) {
                drawingService.getDrawingAll(params.key)
            } else {
                drawingService.getDrawingWithKeyword(keyword, params.key)
            }
           LoadResult.Page(
                    data = response.data.toDomain(),
                    prevKey = null,
                    nextKey = response.nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<DocumentSnapshot, Drawing>): DocumentSnapshot? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.nextKey
        }
    }
}