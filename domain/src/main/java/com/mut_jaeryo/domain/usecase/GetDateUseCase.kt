package com.mut_jaeryo.domain.usecase

import com.mut_jaeryo.domain.UseCase
import com.mut_jaeryo.domain.entities.User
import com.mut_jaeryo.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetDateUseCase @Inject constructor(
        private val userRepository: UserRepository
) : UseCase<Any, String?>() {
    override suspend fun execute(parameter: Any) =
            userRepository.getDate()
}