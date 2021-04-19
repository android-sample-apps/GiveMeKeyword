package com.mut_jaeryo.data.source.drawing

import com.mut_jaeryo.data.mapper.toData
import com.mut_jaeryo.data.mapper.toDomain
import com.mut_jaeryo.domain.entities.Drawing
import com.mut_jaeryo.domain.entities.Favorite
import com.mut_jaeryo.domain.repositories.DrawingRepository
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
        return remoteDrawingDataSource.getFavoriteList(drawing).toDomain()
    }

    override suspend fun getDrawingListAll(): List<Drawing> {
        return remoteDrawingDataSource.getDrawingListAll().toDomain()
    }

    override suspend fun getDrawingListWithKeyword(keyword: String): List<Drawing> {
        return remoteDrawingDataSource.getDrawingListWithKeyword(keyword).toDomain()
    }
}