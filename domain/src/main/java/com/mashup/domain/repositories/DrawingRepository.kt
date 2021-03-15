package com.mashup.domain.repositories

import com.mashup.domain.entities.Drawing

interface DrawingRepository {
    suspend fun uploadImage(drawing: Drawing)
}