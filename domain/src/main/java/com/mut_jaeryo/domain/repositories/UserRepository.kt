package com.mut_jaeryo.domain.repositories

import com.mut_jaeryo.domain.entities.User

interface UserRepository {
    suspend fun getUser() : User?

    suspend fun createUser(user: User)

    suspend fun getDate(): String?

    suspend fun setDate(date: String)
}