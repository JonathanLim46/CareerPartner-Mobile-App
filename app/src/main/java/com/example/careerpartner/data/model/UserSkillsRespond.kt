package com.example.careerpartner.data.model

import com.google.gson.annotations.SerializedName

data class UserSkillsRespond(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("dataCount") val dataCount: Int
)
