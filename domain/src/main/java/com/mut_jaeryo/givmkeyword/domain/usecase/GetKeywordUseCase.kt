package com.mut_jaeryo.givmkeyword.domain.usecase

import com.mut_jaeryo.givmkeyword.domain.UseCase
import com.mut_jaeryo.givmkeyword.domain.entities.Keyword
import com.mut_jaeryo.givmkeyword.domain.repositories.KeywordRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetKeywordUseCase @Inject constructor(
        private val keywordRepository: KeywordRepository,
        private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UseCase<Any, Keyword>(coroutineDispatcher) {
    override suspend fun execute(parameter: Any) =
            keywordRepository.getKeyword()
}