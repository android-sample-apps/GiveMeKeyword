package com.mashup.domain.usecase

import com.mashup.domain.UseCase
import com.mashup.domain.entities.Drawing
import com.mashup.domain.repositories.DrawingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UploadUseCase @Inject constructor(
        private val repository: DrawingRepository,
        private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UseCase<Drawing, Unit>(coroutineDispatcher){
    override suspend fun execute(parameter: Drawing) {
        repository.uploadImage(parameter)
    }
}