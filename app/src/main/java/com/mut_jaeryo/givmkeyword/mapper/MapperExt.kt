package com.mut_jaeryo.givmkeyword.mapper

import com.mut_jaeryo.givmkeyword.domain.entities.Drawing
import com.mut_jaeryo.givmkeyword.domain.entities.Favorite
import com.mut_jaeryo.givmkeyword.entities.DrawingItem
import com.mut_jaeryo.givmkeyword.entities.FavoriteItem

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