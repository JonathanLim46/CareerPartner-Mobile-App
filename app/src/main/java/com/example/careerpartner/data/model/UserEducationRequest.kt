package com.example.careerpartner.data.model

import com.google.gson.annotations.SerializedName

data class UserEducationRequest(
    @SerializedName("institution_name") val institutionName: String,
    @SerializedName("field_of_study") val fieldOfStudy: String,
    @SerializedName("start_year") val startYear: String,
    @SerializedName("end_year") val endYear: String
)
