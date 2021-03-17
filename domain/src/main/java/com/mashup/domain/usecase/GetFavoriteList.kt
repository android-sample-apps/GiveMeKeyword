package com.mashup.domain.usecase

import com.mashup.domain.UseCase
import com.mashup.domain.entities.Drawing
import com.mashup.domain.entities.Favorite
import com.mashup.domain.repositories.DrawingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetFavoriteList @Inject constructor(
        private val drawingRepository: DrawingRepository,
        private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UseCase<Drawing, List<Favorite>>(coroutineDispatcher) {
    override suspend fun execute(parameter: Drawing): List<Favorite> {
        return drawingRepository.getFavoriteList(parameter)
    }
}