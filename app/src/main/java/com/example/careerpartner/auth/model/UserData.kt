package com.example.careerpartner.auth.model

import com.google.gson.annotations.SerializedName

data class UserData(@SerializedName("id") var id: Int,
                    @SerializedName("full_name") var full_name: String,
                    @SerializedName("phone") var phone: String,
                    @SerializedName("username") var username: String,
                    @SerializedName("email") var email: String,
                    @SerializedName("profile_picture") var profile_picture: String)
