package com.example.keystorepractice.data.dto


import com.google.gson.annotations.SerializedName

data class SecureSignInRequest(
    @SerializedName("id")
    val id: String,
    @SerializedName("pw")
    val pw: String,
    @SerializedName("publicKey")
    val publicKey: String,
)