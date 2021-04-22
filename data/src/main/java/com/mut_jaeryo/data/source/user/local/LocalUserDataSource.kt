package com.mut_jaeryo.data.source.user.local

import android.content.Context
import com.mut_jaeryo.data.db.MyPreferences
import com.mut_jaeryo.data.dto.UserModel
import com.mut_jaeryo.data.source.user.UserDataSource
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalUserDataSource @Inject constructor(
        @ApplicationContext private val context: Context
) : UserDataSource {
    override suspend fun getUser(): UserModel? =
            MyPreferences.getName(context)?.let { name ->
                UserModel(name, 0)
            }

    override suspend fun createUser(user: UserModel) {
        MyPreferences.setName(context, user.name)
    }
}