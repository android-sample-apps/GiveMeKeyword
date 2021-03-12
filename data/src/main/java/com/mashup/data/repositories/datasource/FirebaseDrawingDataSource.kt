package com.mashup.data.repositories.datasource

class FirebaseDrawingDataSource : RemoteDrawingDataSource {
    override suspend fun uploadDrawing() {
//        FirebaseDB.saveDrawing(this, BasicDB.getKeyword(applicationContext)!!, SaveUtils.drawingImage!!, BasicDB.getName(applicationContext)!!, data!!.getStringExtra("content")
//                ?: "")
    }
}