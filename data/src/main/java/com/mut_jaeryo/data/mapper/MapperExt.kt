package com.mut_jaeryo.data.mapper

import com.mut_jaeryo.data.dto.*
import com.mut_jaeryo.domain.entities.Comment
import com.mut_jaeryo.domain.entities.Drawing
import com.mut_jaeryo.domain.entities.Keyword
import com.mut_jaeryo.domain.entities.User

fun DrawingModel.toDomain() = Drawing(
        id = id, keyword = keyword,
        userName = userName, imageUrl = imageUrl,
        content = content, heartCount = heartCount,
        isHeart = isHeart
)

fun CommentModel.toDomain() = Comment(
        userId = userId,
        id = id,
        comment = comment,
        drawingId = drawingId,
        isMyComment = isMyComment
)

fun Drawing.toData() = DrawingModel(
        userName = this.userName,
        content = this.content,
        keyword = this.keyword,
        imageUrl = this.imageUrl
)

fun KeywordModel.toDomain() = Keyword(
        keyword = this.keyword
)

fun UserModel.toDomain() = User(
        name = this.name,
        like = this.like
)

fun User.toData() = UserModel(
        name = this.name,
        like = this.like
)

fun Comment.toData() = CommentModel(
        userId = userId,
        id = id,
        comment = comment,
        drawingId = drawingId,
        isMyComment = isMyComment
)

@JvmName("toDomainDrawingModel")
fun List<DrawingModel>.toDomain() = this.map {
    it.toDomain()
}

fun List<CommentModel>.toDomain() = this.map {
    it.toDomain()
}
