package com.mut_jaeryo.domain.entities

data class Comment(
        val id: String = "",
        val userId: String,
        val comment: String,
        val drawingId: String
        )
