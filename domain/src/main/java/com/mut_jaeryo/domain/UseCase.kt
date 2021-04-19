package com.mut_jaeryo.domain

import kotlinx.coroutines.withContext
import com.mut_jaeryo.domain.common.Result
import kotlinx.coroutines.Dispatchers

abstract class UseCase<P, R>() {

    protected abstract suspend fun execute(parameter: P) : R

    suspend operator fun invoke(parameter: P) : Result<R> {
        return try {
            withContext(Dispatchers.IO) {
                val result = execute(parameter)
                Result.Success(result)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}