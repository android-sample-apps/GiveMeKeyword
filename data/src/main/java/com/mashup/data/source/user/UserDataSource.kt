package com.mashup.data.source.user

import com.mashup.data.dto.UserModel

interface UserDataSource {
    suspend fun getUser() : UserModel

    suspend fun createUser(user: UserModel)
}