package com.example.careerpartner.data.model

import com.google.gson.annotations.SerializedName

data class UserAchievementsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: Achievements
)

data class Achievements(
    @SerializedName("achievements") val achievements: List<Achievement>
)

data class Achievement(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("nomination") val nomination: String,
    @SerializedName("year") val year: String
)
