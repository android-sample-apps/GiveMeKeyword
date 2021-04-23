package com.mut_jaeryo.domain.usecase

import androidx.paging.PagingData
import com.mut_jaeryo.domain.UseCase
import com.mut_jaeryo.domain.entities.Drawing
import com.mut_jaeryo.domain.repositories.DrawingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDrawingKeywordUseCase @Inject constructor(
        private val drawingRepository: DrawingRepository
) : UseCase<String, Flow<PagingData<Drawing>>>() {
    override suspend fun execute(parameter: String) =
        drawingRepository.getDrawingListWithKeyword(parameter)
}