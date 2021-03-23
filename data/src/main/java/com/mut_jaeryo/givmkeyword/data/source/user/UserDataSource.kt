package com.mut_jaeryo.givmkeyword.data.source.user

import com.mut_jaeryo.givmkeyword.data.dto.UserModel

interface UserDataSource {
    suspend fun getUser() : UserModel

    suspend fun createUser(user: UserModel)
}