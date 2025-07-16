package com.example.careerpartner.data.model

import com.google.gson.annotations.SerializedName

data class InternshipsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: List<InternshipData>
)

data class InternshipData(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image_cover") val imageCover: String,
    @SerializedName("location") val location: String,
    @SerializedName("responsibilities") val responsibilities: String,
    @SerializedName("requirements") val requirements: String,
    @SerializedName("offer") val offer: String,
    @SerializedName("status") val status: String
)