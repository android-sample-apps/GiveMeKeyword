package com.mut_jaeryo.domain.usecase

import com.mut_jaeryo.domain.UseCase
import com.mut_jaeryo.domain.entities.User
import com.mut_jaeryo.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
        private val userRepository: UserRepository
) : UseCase<User, Unit>() {
    override suspend fun execute(parameter: User) {
        userRepository.createUser(parameter)
    }
}