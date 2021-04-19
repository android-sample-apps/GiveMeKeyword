package com.mut_jaeryo.presentation.mapper

import com.mut_jaeryo.domain.entities.Drawing
import com.mut_jaeryo.domain.entities.Favorite
import com.mut_jaeryo.presentation.entities.DrawingItem
import com.mut_jaeryo.presentation.entities.FavoriteItem

fun FavoriteItem.toDomain() = Favorite(
        name = this.name
)

fun DrawingItem.toDomain() = Drawing(
        userName = this.name,
        keyword = this.keyword,
        imageUrl = "",
        content = this.content
)

fun List<Favorite>.toPresentation() : List<FavoriteItem> {
    return this.map {
        FavoriteItem(name = it.name)
    }
}

@JvmName("toPresentationDrawing")
fun List<Drawing>.toPresentation() : List<DrawingItem> {
    return this.map {
        DrawingItem(id = it.id, keyword = it.keyword, name = it.userName,
                content = it.content, heartCount = it.heartCount, isHeart = it.isHeart)
    }
}