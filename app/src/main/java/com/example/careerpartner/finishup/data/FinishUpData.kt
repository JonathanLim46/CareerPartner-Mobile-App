package com.example.careerpartner.finishup.data

import com.example.careerpartner.data.model.UserAchievementsRequest
import com.example.careerpartner.data.model.UserEducationRequest
import com.example.careerpartner.data.model.UserInterestsRequest
import com.example.careerpartner.data.model.UserSkillsRequest

data class FinishUpData(
    val educationInput: List<UserEducationRequest>? = emptyList(),
    val interestInput: List<UserInterestsRequest>? = emptyList(),
    val skillInput: List<UserSkillsRequest>? = emptyList(),
    val experienceInput: List<UserAchievementsRequest>? = emptyList()
)
