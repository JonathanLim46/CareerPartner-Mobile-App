package com.example.careerpartner.data.model

import com.google.gson.annotations.SerializedName

data class UserInterestsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("dataCount") val dataCount: Int
)
