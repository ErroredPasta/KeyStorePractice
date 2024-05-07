package com.example.keystorepractice.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object AuthApiInstance {
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    private val gsonConverterFactory by lazy {
        GsonConverterFactory.create()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    val authApi: AuthApi = retrofit.create()
}