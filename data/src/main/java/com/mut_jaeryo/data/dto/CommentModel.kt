package com.mut_jaeryo.data.dto

data class CommentModel(
        val id: String = "",
        val drawingId: String,
        val userId: String,
        val comment: String,
        val isMyComment: Boolean
        )
