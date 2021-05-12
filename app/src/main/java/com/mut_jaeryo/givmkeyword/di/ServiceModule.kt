package com.mut_jaeryo.givmkeyword.di

import com.mut_jaeryo.data.api.comment.CommentService
import com.mut_jaeryo.data.api.comment.FirebaseCommentServiceImpl
import com.mut_jaeryo.data.api.drawing.DrawingService
import com.mut_jaeryo.data.api.drawing.FirebaseDrawingServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun bindDrawingService(
            firebaseDrawingServiceImpl: FirebaseDrawingServiceImpl
    ): DrawingService

    @Binds
    abstract fun bindCommentService(
            firebaseCommentServiceImpl: FirebaseCommentServiceImpl
    ): CommentService

}