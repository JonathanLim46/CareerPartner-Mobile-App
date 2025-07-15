package com.example.careerpartner.data.model

import com.google.gson.annotations.SerializedName

data class UserInterestsRequest(
    @SerializedName("interests") val interests: List<Interest>
)

data class Interest(
    @SerializedName("name") val name: String
)
