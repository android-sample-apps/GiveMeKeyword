package com.mut_jaeryo.domain.usecase

import androidx.paging.PagingData
import com.mut_jaeryo.domain.UseCase
import com.mut_jaeryo.domain.entities.Comment
import com.mut_jaeryo.domain.repositories.CommentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommentUseCase @Inject constructor(
        private val commentRepository: CommentRepository
) : UseCase<Pair<String, String>, Flow<PagingData<Comment>>>() {

    override suspend fun execute(parameter: Pair<String, String>) =
            commentRepository.getComments(
                    drawingId = parameter.first,
                    userId = parameter.second
            )
}