package com.example.careerpartner.auth.model

import com.google.gson.annotations.SerializedName

data class LogoutResponse(@SerializedName("status") val status: String, @SerializedName("message") val message: String)
