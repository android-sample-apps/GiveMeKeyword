package com.mut_jaeryo.givmkeyword.data.source.drawing

import com.mut_jaeryo.givmkeyword.data.mapper.toData
import com.mut_jaeryo.givmkeyword.domain.entities.Drawing
import com.mut_jaeryo.givmkeyword.domain.entities.Favorite
import com.mut_jaeryo.givmkeyword.domain.repositories.DrawingRepository
import javax.inject.Inject

class DrawingRepositoryImpl @Inject constructor(
        private val remoteDrawingDataSource: DrawingDataSource
) : DrawingRepository {
    override suspend fun uploadImage(drawing: Drawing) {
        remoteDrawingDataSource.uploadDrawing(drawing.toData())
    }

    override suspend fun reportDrawing(drawing: Drawing) {

    }

    override suspend fun changeDrawingHeart(drawing: Drawing) {

    }

    override suspend fun getFavoriteList(drawing: Drawing): List<Favorite> {
        return emptyList()
    }
}