package com.mut_jaeryo.domain.usecase

import android.graphics.Bitmap
import com.mut_jaeryo.domain.UseCase
import com.mut_jaeryo.domain.repositories.DrawingRepository
import javax.inject.Inject

class GetDrawingCachePathUseCase @Inject constructor(
        private val drawingRepository: DrawingRepository
) : UseCase<Bitmap, String?>() {
    override suspend fun execute(parameter: Bitmap): String? {
        return drawingRepository.getDrawingCachePath(parameter)
    }
}