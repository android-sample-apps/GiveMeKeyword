package com.mut_jaeryo.presentation.mapper

import com.mut_jaeryo.domain.entities.Drawing
import com.mut_jaeryo.presentation.entities.DrawingItem

fun DrawingItem.toDomain() = Drawing(
        userName = this.name,
        keyword = this.keyword,
        imageUrl = "",
        content = this.content
)

fun Drawing.toPresentation() =  DrawingItem(id = id, keyword = keyword, name = userName,
        content = content, heartCount = heartCount, isHeart = isHeart)

@JvmName("toPresentationDrawing")
fun List<Drawing>.toPresentation() : List<DrawingItem> {
    return this.map {
        DrawingItem(id = it.id, keyword = it.keyword, name = it.userName,
                content = it.content, heartCount = it.heartCount, isHeart = it.isHeart)
    }
}