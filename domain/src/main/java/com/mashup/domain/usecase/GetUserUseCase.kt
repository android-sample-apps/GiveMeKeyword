package com.mashup.domain.usecase

import com.mashup.domain.UseCase
import com.mashup.domain.entities.User
import com.mashup.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
        private val userRepository: UserRepository,
        private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UseCase<Any, User>(coroutineDispatcher) {
    override suspend fun execute(parameter: Any) =
            userRepository.getUser()
}