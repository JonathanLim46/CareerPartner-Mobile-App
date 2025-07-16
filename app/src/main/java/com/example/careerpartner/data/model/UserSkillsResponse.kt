package com.example.careerpartner.data.model

import com.google.gson.annotations.SerializedName

data class UserSkillsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("dataCount") val dataCount: Int
)
