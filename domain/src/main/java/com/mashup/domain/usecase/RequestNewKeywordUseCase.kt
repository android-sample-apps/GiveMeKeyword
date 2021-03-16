package com.mashup.domain.usecase

import com.mashup.domain.UseCase
import com.mashup.domain.entities.Keyword
import com.mashup.domain.repositories.KeywordRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RequestNewKeywordUseCase @Inject constructor(
        private val keywordRepository: KeywordRepository,
        private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UseCase<Any, Keyword>(coroutineDispatcher) {
    override suspend fun execute(parameter: Any) =
            keywordRepository.requestNewKeyword()
}