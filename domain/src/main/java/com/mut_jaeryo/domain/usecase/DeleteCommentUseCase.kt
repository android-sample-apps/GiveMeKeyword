package com.mut_jaeryo.domain.usecase

import com.mut_jaeryo.domain.UseCase
import com.mut_jaeryo.domain.entities.Comment
import com.mut_jaeryo.domain.entities.User
import com.mut_jaeryo.domain.repositories.CommentRepository
import com.mut_jaeryo.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DeleteCommentUseCase @Inject constructor(
        private val commentRepository: CommentRepository
) : UseCase<Comment, Unit>() {
    override suspend fun execute(parameter: Comment) {
        commentRepository.deleteComment(parameter)
    }
}