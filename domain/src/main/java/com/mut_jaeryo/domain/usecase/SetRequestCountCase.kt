package com.mut_jaeryo.domain.usecase

import com.mut_jaeryo.domain.UseCase
import com.mut_jaeryo.domain.entities.User
import com.mut_jaeryo.domain.repositories.KeywordRepository
import com.mut_jaeryo.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class SetRequestCountCase @Inject constructor(
        private val keywordRepository: KeywordRepository
) : UseCase<Int, Unit>() {
    override suspend fun execute(parameter: Int) =
            keywordRepository.setRequestCount(parameter)
}