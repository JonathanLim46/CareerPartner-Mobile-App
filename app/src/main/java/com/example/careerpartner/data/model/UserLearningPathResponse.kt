package com.example.careerpartner.data.model

import com.google.gson.annotations.SerializedName

data class UserLearningPathResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: List<UserLearningPathData>
)

data class UserLearningPathData(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("source") val source: String,
    @SerializedName("url") val url: String,
    @SerializedName("is_done") val isDone: String
)
