package com.example.careerpartner.data.model

import com.google.gson.annotations.SerializedName

class UserAchievementsRequest(
    @SerializedName("title") val title: String,
    @SerializedName("nomination") val nominationData: String,
    @SerializedName("year") val year: String
)