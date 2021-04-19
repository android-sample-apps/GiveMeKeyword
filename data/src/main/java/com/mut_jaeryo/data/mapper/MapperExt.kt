package com.mut_jaeryo.data.mapper

import com.mut_jaeryo.data.dto.DrawingModel
import com.mut_jaeryo.data.dto.FavoriteModel
import com.mut_jaeryo.data.dto.KeywordModel
import com.mut_jaeryo.data.dto.UserModel
import com.mut_jaeryo.domain.entities.Drawing
import com.mut_jaeryo.domain.entities.Favorite
import com.mut_jaeryo.domain.entities.Keyword
import com.mut_jaeryo.domain.entities.User

fun DrawingModel.toDomain() = Drawing(
        userName = this.userName,
        content = this.content,
        keyword = this.keyword,
        imageUrl = this.imageUrl
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

@JvmName("toDomainDrawingModel")
fun List<DrawingModel>.toDomain() = this.map {
    Drawing(id = it.id, keyword = it.keyword, userName = it.userName,
    imageUrl = it.imageUrl, content = it.content, heartCount = it.heartCount, isHeart = it.isHeart)
}

fun List<FavoriteModel>.toDomain() = this.map {
    Favorite(name = it.name)
}