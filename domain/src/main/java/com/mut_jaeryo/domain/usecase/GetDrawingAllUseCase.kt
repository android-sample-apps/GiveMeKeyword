package com.mut_jaeryo.domain.usecase

import com.mut_jaeryo.domain.UseCase
import com.mut_jaeryo.domain.entities.Drawing
import com.mut_jaeryo.domain.repositories.DrawingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetDrawingAllUseCase @Inject constructor(
        private val drawingRepository: DrawingRepository
) : UseCase<Unit, List<Drawing>>() {
    override suspend fun execute(parameter: Unit): List<Drawing> {
        return drawingRepository.getDrawingListAll()
    }
}