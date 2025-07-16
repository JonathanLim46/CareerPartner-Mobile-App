package com.example.careerpartner.data.model

import com.google.gson.annotations.SerializedName

data class UserSkillsAllRespond(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: Skills
)

data class Skills(
    @SerializedName("skills") val skills: List<SkillName>
)

data class SkillName(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
