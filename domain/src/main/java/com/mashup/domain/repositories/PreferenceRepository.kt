package com.mashup.domain.repositories

import com.mashup.domain.common.Result

interface PreferenceRepository {
    suspend fun getWork() : Result<Int>

    suspend fun setWork() : Result<Int>
}
