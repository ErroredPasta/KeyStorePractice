package com.example.keystorepractice.domain

interface AuthRepository {
    suspend fun signIn(id: String, pw: String): String
    suspend fun secureSignIn(id: String, pw: String): String
}