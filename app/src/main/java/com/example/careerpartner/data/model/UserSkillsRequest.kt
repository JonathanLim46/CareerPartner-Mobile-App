package com.example.careerpartner.data.model

import com.google.gson.annotations.SerializedName

data class UserSkillsRequest(
    @SerializedName("skills") val skills: List<Skill>
)

data class Skill(
    @SerializedName("name") val name: String
)
