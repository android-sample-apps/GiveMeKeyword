package com.mut_jaeryo.givmkeyword.domain.usecase

import com.mut_jaeryo.givmkeyword.domain.UseCase
import com.mut_jaeryo.givmkeyword.domain.entities.User
import com.mut_jaeryo.givmkeyword.domain.repositories.UserRepository
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