package com.example.careerpartner.auth.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(@SerializedName("identifier") var identifier: String,
                        @SerializedName("password") var password: String)
