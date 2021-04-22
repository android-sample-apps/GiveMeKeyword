package com.mut_jaeryo.domain.usecase

import com.mut_jaeryo.domain.UseCase
import com.mut_jaeryo.domain.entities.Keyword
import com.mut_jaeryo.domain.repositories.KeywordRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RequestNewKeywordUseCase @Inject constructor(
        private val keywordRepository: KeywordRepository
) : UseCase<Any, Keyword>() {
    override suspend fun execute(parameter: Any) =
            keywordRepository.requestNewKeyword()
}