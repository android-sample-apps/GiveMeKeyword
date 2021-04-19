package com.mut_jaeryo.domain.usecase

import com.mut_jaeryo.domain.UseCase
import com.mut_jaeryo.domain.entities.Drawing
import com.mut_jaeryo.domain.entities.Favorite
import com.mut_jaeryo.domain.repositories.DrawingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetFavoriteListUseCase @Inject constructor(
        private val drawingRepository: DrawingRepository
) : UseCase<Drawing, List<Favorite>>() {
    override suspend fun execute(parameter: Drawing): List<Favorite> {
        return drawingRepository.getFavoriteList(parameter)
    }
}