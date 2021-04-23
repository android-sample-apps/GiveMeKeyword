package com.mut_jaeryo.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.mut_jaeryo.domain.UseCase
import com.mut_jaeryo.domain.entities.Drawing
import com.mut_jaeryo.domain.repositories.DrawingRepository
import javax.inject.Inject

class GetDrawingAllUseCase @Inject constructor(
        private val drawingRepository: DrawingRepository
) : UseCase<Unit, LiveData<PagingData<Drawing>>>() {
    override suspend fun execute(parameter: Unit): LiveData<PagingData<Drawing>> {
        return drawingRepository.getDrawingListAll()
    }
}