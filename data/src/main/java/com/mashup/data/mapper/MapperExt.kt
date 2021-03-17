package com.mashup.data.mapper

import com.mashup.data.dto.DrawingModel
import com.mashup.data.dto.KeywordModel
import com.mashup.data.dto.UserModel
import com.mashup.domain.entities.Drawing
import com.mashup.domain.entities.Keyword
import com.mashup.domain.entities.User

fun DrawingModel.toDomain() = Drawing(
        userName = this.userName,
        content = this.content,
        keyword = this.keyword,
        imageUrl = this.drawing
)

fun Drawing.toData() = DrawingModel(
        userName = this.userName,
        content = this.content,
        keyword = this.keyword,
        drawing = this.imageUrl
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