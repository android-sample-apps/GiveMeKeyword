package com.mut_jaeryo.data.source.drawing

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.google.firebase.firestore.DocumentSnapshot
import com.mut_jaeryo.data.mapper.toData
import com.mut_jaeryo.data.mapper.toDomain
import com.mut_jaeryo.domain.entities.Drawing
import com.mut_jaeryo.domain.repositories.DrawingRepository
import javax.inject.Inject

class DrawingRepositoryImpl @Inject constructor(
        private val localDrawingDataSource: DrawingDataSource,
        private val remoteDrawingDataSource: DrawingDataSource
) : DrawingRepository {
    override suspend fun uploadImage(drawing: Drawing) {
        remoteDrawingDataSource.uploadDrawing(drawing.toData())
    }

    override suspend fun reportDrawing(drawing: Drawing) {
        remoteDrawingDataSource.reportDrawing(drawing.toData())
    }

    override suspend fun changeDrawingHeart(drawing: Drawing) {
        remoteDrawingDataSource.changeDrawingHeart(drawing.toData())
    }

    override suspend fun getDrawingListAll(): LiveData<PagingData<Drawing>> {
        return remoteDrawingDataSource.getDrawingListAll()!!.liveData
    }

    override suspend fun getDrawingCachePath(bitmap: Bitmap): String? {
        return localDrawingDataSource.getDrawingCachePath(bitmap)
    }

    override suspend fun getDrawingListWithKeyword(keyword: String): LiveData<PagingData<Drawing>> {
        return remoteDrawingDataSource.getDrawingListWithKeyword(keyword)!!.liveData
    }
}