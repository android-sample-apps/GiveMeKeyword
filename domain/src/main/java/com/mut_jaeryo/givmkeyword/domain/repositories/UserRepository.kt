package com.mut_jaeryo.givmkeyword.domain.repositories

import com.mut_jaeryo.givmkeyword.domain.entities.User

interface UserRepository {
    suspend fun getUser() : User

    suspend fun createUser(user: User)
}