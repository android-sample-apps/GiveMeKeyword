package com.mashup.data.source.user

import com.mashup.data.mapper.toData
import com.mashup.data.mapper.toDomain
import com.mashup.domain.entities.User
import com.mashup.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localUserDataSource: UserDataSource,
    private val remoteUserDataSource: UserDataSource
) : UserRepository {
    override suspend fun getUser(): User {
        return localUserDataSource.getUser().toDomain()
    }

    override suspend fun createUser(user: User) {
        localUserDataSource.createUser(user.toData())
        remoteUserDataSource.createUser(user.toData())
    }
}