package com.mut_jaeryo.givmkeyword.domain.usecase

import com.mut_jaeryo.givmkeyword.domain.UseCase
import com.mut_jaeryo.givmkeyword.domain.repositories.PreferenceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class UpdateWorkUseCase @Inject constructor(
        private val repository: PreferenceRepository,
        private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UseCase<Unit, Unit>(coroutineDispatcher){
    override suspend fun execute(parameter: Unit) {
        repository.uploadWork()
    }
}