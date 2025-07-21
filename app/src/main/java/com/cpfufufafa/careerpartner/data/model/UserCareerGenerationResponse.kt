package com.cpfufufafa.careerpartner.data.model

import com.google.gson.annotations.SerializedName

data class UserCareerGenerationResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: CareerGenerationData
)

data class CareerGenerationData(
    @SerializedName("goal_career") val goalCareer: String,
    @SerializedName("description") val description: String,
    @SerializedName("expected_salary") val expectedSalary: String,
    @SerializedName("job_opportunity") val jobOpportunity: String
)
