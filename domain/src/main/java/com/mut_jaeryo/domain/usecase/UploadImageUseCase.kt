package com.mut_jaeryo.domain.usecase

import com.mut_jaeryo.domain.UseCase
import com.mut_jaeryo.domain.entities.Drawing
import com.mut_jaeryo.domain.repositories.DrawingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
        private val repository: DrawingRepository,
) : UseCase<Drawing, Unit>(){
    override suspend fun execute(parameter: Drawing) {
        repository.uploadImage(parameter)
    }
}