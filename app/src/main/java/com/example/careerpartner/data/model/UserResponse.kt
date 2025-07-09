package com.example.careerpartner.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(@SerializedName("status") val status: String, @SerializedName("data") val data: TalentData)
