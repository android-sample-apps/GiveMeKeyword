package com.mut_jaeryo.data.source.drawing.local

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.mut_jaeryo.data.dto.DrawingModel
import com.mut_jaeryo.data.dto.FavoriteModel
import com.mut_jaeryo.data.source.drawing.DrawingDataSource
import com.mut_jaeryo.domain.entities.Drawing
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class LocalDrawingDataSourceImpl @Inject constructor(
        @ApplicationContext private val context: Context
) : DrawingDataSource {
    override suspend fun uploadDrawing(drawingModel: DrawingModel) {

    }

    override suspend fun getDrawingListAll(): List<DrawingModel> {
        return emptyList()
    }

    override suspend fun getDrawingListWithKeyword(keyword: String): List<DrawingModel> {
        return emptyList()
    }

    override suspend fun reportDrawing(drawing: Drawing) {
    }

    override suspend fun changeDrawingHeart(drawing: Drawing) {

    }

    override suspend fun getDrawingCachePath(bitmap: Bitmap): String? {
        val storage: File = context.cacheDir
        val fileName = "cacheImage.jpg"
        return try {
            val imageFile = File.createTempFile(fileName, null, storage)
            val out = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)

            out.close()
            imageFile.path
        } catch (e: FileNotFoundException) {
            null
        } catch (e: IOException) {
            null
        } catch (e: Exception) {
            null
        }
    }
}