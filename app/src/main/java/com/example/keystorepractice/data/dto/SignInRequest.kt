package com.example.keystorepractice.data.dto


import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("id")
    val id: String,
    @SerializedName("pw")
    val pw: String
)