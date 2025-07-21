package com.cpfufufafa.careerpartner.finishup.data

import com.cpfufufafa.careerpartner.data.model.UserAchievementsRequest
import com.cpfufufafa.careerpartner.data.model.UserEducationRequest
import com.cpfufufafa.careerpartner.data.model.UserInterestsRequest
import com.cpfufufafa.careerpartner.data.model.UserSkillsRequest

data class FinishUpData(
    val educationInput: List<UserEducationRequest>? = emptyList(),
    val interestInput: List<UserInterestsRequest>? = emptyList(),
    val skillInput: List<UserSkillsRequest>? = emptyList(),
    val experienceInput: List<UserAchievementsRequest>? = emptyList()
)
