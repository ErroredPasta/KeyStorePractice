package com.example.keystorepractice.domain

interface PreferenceRepository {
    suspend fun saveToken(token: String)
}