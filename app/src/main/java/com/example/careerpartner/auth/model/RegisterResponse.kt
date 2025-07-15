package com.example.careerpartner.auth.model

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: RegisterData
)

data class RegisterData(
    @SerializedName("full_name") val fullName: String,
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("role") val role: String,
)
