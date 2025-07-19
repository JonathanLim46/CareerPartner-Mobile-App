package com.example.careerpartner.data.model

import com.google.gson.annotations.SerializedName

data class VolunteerResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: List<VolunteerData>
)

data class VolunteerData(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("detail_activity") val detailActivity: String,
    @SerializedName("location") val location: String,
    @SerializedName("link") val link: String,
    @SerializedName("status") val status: String,
    @SerializedName("image_cover") val imageCover: String,
)