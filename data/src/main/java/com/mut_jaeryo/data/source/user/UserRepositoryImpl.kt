package com.mut_jaeryo.data.source.user

import com.mut_jaeryo.data.mapper.toData
import com.mut_jaeryo.data.mapper.toDomain
import com.mut_jaeryo.domain.entities.User
import com.mut_jaeryo.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localUserDataSource: UserDataSource,
    private val remoteUserDataSource: UserDataSource
) : UserRepository {
    override suspend fun getUser(): User? {
        return localUserDataSource.getUser()?.toDomain()
    }

    override suspend fun createUser(user: User) {
        localUserDataSource.createUser(user.toData())
        remoteUserDataSource.createUser(user.toData())
    }
}