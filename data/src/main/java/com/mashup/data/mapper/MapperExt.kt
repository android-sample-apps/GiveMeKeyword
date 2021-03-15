package com.mashup.data.mapper

import com.mashup.data.dto.DrawingModel
import com.mashup.domain.entities.Drawing

fun DrawingModel.toDomain() = Drawing(
        userName = this.userName,
        content = this.content,
        keyword = this.keyword,
        drawing = this.drawing
)

fun Drawing.toData() = DrawingModel(
        userName = this.userName,
        content = this.content,
        keyword = this.keyword,
        drawing = this.drawing
)