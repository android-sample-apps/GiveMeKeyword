package com.mashup.domain.repositories

import com.mashup.domain.entities.User

interface UserRepository {
    suspend fun getUser() : User

    suspend fun createUser(user: User)
}