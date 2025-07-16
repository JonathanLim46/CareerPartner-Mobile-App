package com.example.careerpartner.data.model

import com.google.gson.annotations.SerializedName

data class UserInterestsAllRespond(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: InterestsAll
)

data class InterestsAll(
    @SerializedName("interests") val interests: List<InterestName>
)

data class InterestName(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

