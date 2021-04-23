package com.mut_jaeryo.data.source.drawing.local

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.firebase.firestore.DocumentSnapshot
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

    override suspend fun getDrawingListAll(): Pager<DocumentSnapshot, Drawing>? {
        return null
    }

    override suspend fun getDrawingListWithKeyword(keyword: String): Pager<DocumentSnapshot, Drawing>? {
        return null
    }

    override suspend fun reportDrawing(drawingModel: DrawingModel) {
    }

    override suspend fun changeDrawingHeart(drawingModel: DrawingModel) {

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