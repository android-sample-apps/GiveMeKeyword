package com.mut_jaeryo.domain.usecase

import com.mut_jaeryo.domain.UseCase
import com.mut_jaeryo.domain.repositories.KeywordRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetKeywordRequestCountUseCase @Inject constructor(
        private val keywordRepository: KeywordRepository
) : UseCase<Unit, Int>() {
    override suspend fun execute(parameter: Unit) =
            keywordRepository.getRequestCount()
}