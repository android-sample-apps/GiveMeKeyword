package com.mut_jaeryo.data.source.user.remote

import com.mut_jaeryo.data.api.firebase.FirebaseService
import com.mut_jaeryo.data.dto.UserModel
import com.mut_jaeryo.data.source.user.UserDataSource
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
        private val firebaseService: FirebaseService
) : UserDataSource {
    override suspend fun getUser(): UserModel {
        return UserModel("nothing", 0)
    }

    override suspend fun createUser(user: UserModel) {
        return firebaseService.createUser(user)
    }

    override suspend fun getDate(): String? {
        return null
    }

    override suspend fun setDate(date: String) {
    }
}