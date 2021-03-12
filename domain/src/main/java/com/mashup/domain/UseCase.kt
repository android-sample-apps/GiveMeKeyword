package com.mashup.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.mashup.domain.common.Result

abstract class UseCase<P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    protected abstract suspend fun execute(parameter: P) : R

    suspend operator fun invoke(parameter: P) : Result<R> {
        return try {
            withContext(coroutineDispatcher) {
                val result = execute(parameter)
                Result.Success(result)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}