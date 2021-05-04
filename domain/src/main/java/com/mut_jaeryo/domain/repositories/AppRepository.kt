package com.mut_jaeryo.domain.repositories

interface AppRepository {
    suspend fun getAppVersion(): String
}