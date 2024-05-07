package com.example.keystorepractice.domain

interface AuthRepository {
    suspend fun signIn(id: String, pw: String): String
}