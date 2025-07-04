package com.example.careerpartner.auth.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("status") var status: Int,
                         @SerializedName("message") var message: String,
                         @SerializedName("data") var data: Data) {
    data class Data(@SerializedName("token") var token: String, @SerializedName("user") var user: UserData)
}
