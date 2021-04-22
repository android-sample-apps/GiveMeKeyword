package com.mut_jaeryo.data.source.user

import com.mut_jaeryo.data.dto.UserModel

interface UserDataSource {
    suspend fun getUser() : UserModel?

    suspend fun createUser(user: UserModel)

    suspend fun getDate(): String?

    suspend fun setDate(date: String)
}