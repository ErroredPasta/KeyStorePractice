package com.example.keystorepractice.data.api

import com.example.keystorepractice.data.dto.SecureSignInRequest
import com.example.keystorepractice.data.dto.SignInRequest
import com.example.keystorepractice.data.dto.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/signIn")
    suspend fun signIn(
        @Body request: SignInRequest
    ): TokenResponse

    @POST("/auth/secureSignIn")
    suspend fun secureSignIn(
        @Body request: SecureSignInRequest
    ): TokenResponse
}