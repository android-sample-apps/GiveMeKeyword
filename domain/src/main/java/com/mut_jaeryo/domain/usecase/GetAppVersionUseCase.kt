package com.mut_jaeryo.domain.usecase

import com.mut_jaeryo.domain.UseCase
import com.mut_jaeryo.domain.repositories.AppRepository
import javax.inject.Inject

class GetAppVersionUseCase @Inject constructor(
        private val appRepository: AppRepository
) : UseCase<Any, String>() {
    override suspend fun execute(parameter: Any) =
            appRepository.getAppVersion()
}