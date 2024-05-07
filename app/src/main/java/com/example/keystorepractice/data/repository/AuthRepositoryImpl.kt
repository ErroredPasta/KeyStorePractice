package com.example.keystorepractice.data.repository

import com.example.keystorepractice.data.api.AuthApi
import com.example.keystorepractice.data.api.AuthApiInstance
import com.example.keystorepractice.data.dto.SignInRequest
import com.example.keystorepractice.domain.AuthRepository

class AuthRepositoryImpl(
    private val api: AuthApi = AuthApiInstance.authApi
) : AuthRepository {
    override suspend fun signIn(id: String, pw: String): String {
        return api.signIn(SignInRequest(id, pw)).token
    }
}