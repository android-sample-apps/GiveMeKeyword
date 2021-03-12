package com.mashup.data.repositories.datasource

interface RemoteDrawingDataSource {
    suspend fun uploadDrawing()
}