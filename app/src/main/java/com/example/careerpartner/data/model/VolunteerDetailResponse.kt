package com.example.careerpartner.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class VolunteerDetailResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: VolunteerDetailData
)

data class VolunteerDetailData(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("detail_activity") val detailActivity: String,
    @SerializedName("image_cover") val imageCover: String,
    @SerializedName("location") val location: String,
    @SerializedName("status") val status: String,
    @SerializedName("organization") val organization: OrganizationVolunteer
)

data class OrganizationVolunteer(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("logo") val logo: String,
    @SerializedName("description") val description: String,
    @SerializedName("contact_email") val contactEmail: String,
    @SerializedName("contact_phone") val contactPhone: String,
)
