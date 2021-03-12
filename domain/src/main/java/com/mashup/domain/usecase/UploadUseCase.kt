package com.mashup.domain.usecase

import com.mashup.domain.UseCase
import com.mashup.domain.repositories.DrawingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class UploadUseCase(
        private val repository: DrawingRepository,
        private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UseCase<Unit, Unit>(coroutineDispatcher){
    override suspend fun execute(parameter: Unit) {
        repository.uploadImage()
    }
}