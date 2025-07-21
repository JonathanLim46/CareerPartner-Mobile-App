package com.fufufafaAI.careerpartner.data.model

import com.google.gson.annotations.SerializedName

data class UserGenerateLearningPathsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("dataCreated") val dataCreated: Int
)
