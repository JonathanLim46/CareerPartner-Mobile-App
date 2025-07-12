package com.example.careerpartner.data.model

import com.google.gson.annotations.SerializedName

data class UserProjectsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: List<UserProjectData>
)

data class UserProjectData(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("link") val link: String,
    @SerializedName("year") val year: String
)
