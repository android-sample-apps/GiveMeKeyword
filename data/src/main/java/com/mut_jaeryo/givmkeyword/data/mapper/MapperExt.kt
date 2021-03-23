package com.mut_jaeryo.givmkeyword.data.mapper

import com.mut_jaeryo.givmkeyword.data.dto.DrawingModel
import com.mut_jaeryo.givmkeyword.data.dto.KeywordModel
import com.mut_jaeryo.givmkeyword.data.dto.UserModel
import com.mut_jaeryo.givmkeyword.domain.entities.Drawing
import com.mut_jaeryo.givmkeyword.domain.entities.Keyword
import com.mut_jaeryo.givmkeyword.domain.entities.User

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