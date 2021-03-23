package com.mut_jaeryo.givmkeyword.domain.usecase

import com.mut_jaeryo.givmkeyword.domain.UseCase
import com.mut_jaeryo.givmkeyword.domain.entities.Drawing
import com.mut_jaeryo.givmkeyword.domain.entities.Favorite
import com.mut_jaeryo.givmkeyword.domain.repositories.DrawingRepository
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