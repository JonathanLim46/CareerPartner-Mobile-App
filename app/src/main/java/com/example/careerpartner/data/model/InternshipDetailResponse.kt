package com.example.careerpartner.data.model

import com.google.gson.annotations.SerializedName

data class InternshipDetailResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: InternshipDetailData
)

data class InternshipDetailData(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image_cover") val imageCover: String,
    @SerializedName("location") val location: String,
    @SerializedName("responsibilities") val responsibilities: String,
    @SerializedName("requirements") val requirements: String,
    @SerializedName("offer") val offer: String,
    @SerializedName("status") val status: String,
    @SerializedName("company") val company: CompanyData
)

data class CompanyData(
    @SerializedName("name") val name: String,
    @SerializedName("logo") val logo: String,
    @SerializedName("description") val description: String,
    @SerializedName("industry") val industry: String,
    @SerializedName("headquartersAddress") val headquartersAddress: String,
    @SerializedName("website") val website: String,
    @SerializedName("contact_email") val contactEmail: String,
    @SerializedName("contact_phone") val contactPhone: String,
)