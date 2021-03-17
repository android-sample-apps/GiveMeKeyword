package com.mashup.data.source.user.local

import android.content.Context
import com.mashup.data.db.MyPreferences
import com.mashup.data.dto.UserModel
import com.mashup.data.source.user.UserDataSource
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class LocalUserDataSource @Inject constructor(
        @ActivityContext private val context: Context
) : UserDataSource {
    override suspend fun getUser(): UserModel =
        UserModel(MyPreferences.getName(context) ?: "알 수없음", 0)

    override suspend fun createUser(user: UserModel) {
        MyPreferences.setName(context, user.name)
    }
}