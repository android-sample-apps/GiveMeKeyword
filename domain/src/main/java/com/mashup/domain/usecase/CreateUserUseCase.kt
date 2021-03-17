package com.mashup.domain.usecase

import com.mashup.domain.UseCase
import com.mashup.domain.entities.User
import com.mashup.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
        private val userRepository: UserRepository,
        private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UseCase<User, Unit>(coroutineDispatcher) {
    override suspend fun execute(parameter: User) {
        userRepository.createUser(parameter)
    }
}