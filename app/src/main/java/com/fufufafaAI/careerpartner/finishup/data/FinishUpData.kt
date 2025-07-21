package com.fufufafaAI.careerpartner.finishup.data

import com.fufufafaAI.careerpartner.data.model.UserAchievementsRequest
import com.fufufafaAI.careerpartner.data.model.UserEducationRequest
import com.fufufafaAI.careerpartner.data.model.UserInterestsRequest
import com.fufufafaAI.careerpartner.data.model.UserSkillsRequest

data class FinishUpData(
    val educationInput: List<UserEducationRequest>? = emptyList(),
    val interestInput: List<UserInterestsRequest>? = emptyList(),
    val skillInput: List<UserSkillsRequest>? = emptyList(),
    val experienceInput: List<UserAchievementsRequest>? = emptyList()
)
