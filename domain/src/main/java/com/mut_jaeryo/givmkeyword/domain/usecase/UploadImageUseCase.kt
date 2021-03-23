package com.mut_jaeryo.givmkeyword.domain.usecase

import com.mut_jaeryo.givmkeyword.domain.UseCase
import com.mut_jaeryo.givmkeyword.domain.entities.Drawing
import com.mut_jaeryo.givmkeyword.domain.repositories.DrawingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
        private val repository: DrawingRepository,
        private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UseCase<Drawing, Unit>(coroutineDispatcher){
    override suspend fun execute(parameter: Drawing) {
        repository.uploadImage(parameter)
    }
}