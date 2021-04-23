package com.mut_jaeryo.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.mut_jaeryo.domain.UseCase
import com.mut_jaeryo.domain.entities.Drawing
import com.mut_jaeryo.domain.repositories.DrawingRepository
import javax.inject.Inject

class GetDrawingKeywordUseCase @Inject constructor(
        private val drawingRepository: DrawingRepository
) : UseCase<String, LiveData<PagingData<Drawing>>>() {
    override suspend fun execute(parameter: String): LiveData<PagingData<Drawing>> {
        return drawingRepository.getDrawingListWithKeyword(parameter)
    }
}