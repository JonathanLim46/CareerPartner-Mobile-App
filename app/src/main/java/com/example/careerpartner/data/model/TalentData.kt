package com.example.careerpartner.data.model

import com.example.careerpartner.auth.model.UserData
import com.google.gson.annotations.SerializedName
import java.io.Serial

data class TalentData(
    @SerializedName("talent") val talent: UserTalentData,
)

data class UserTalentData(
    @SerializedName("id") var id: Int,
    @SerializedName("full_name") var full_name: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("username") var username: String,
    @SerializedName("email") var email: String,
    @SerializedName("role") var role: String,
    @SerializedName("profile_picture") var profile_picture: String,
    @SerializedName("talent") var talent: Talent
)

data class Talent(
    @SerializedName("current_education") val currentEducation: String,
    @SerializedName("goal_career") val goalCareer: String,
    @SerializedName("description") val description: String,
    @SerializedName("expected_salary") val expectedSalary: String,
    @SerializedName("date_of_birth") val dataOfBirth: String,
    @SerializedName("skills") val skills: List<SkillData>,
    @SerializedName("experiences") val experiences: List<ExperienceData>,
    @SerializedName("projects") val projects: List<ProjectData>,
    @SerializedName("achievements") val achievements: List<AchievementData>,
    @SerializedName("interests") val interests: List<InterestData>
)

data class SkillData(@SerializedName("id") val id: Int, @SerializedName("name") val name: String)

data class ExperienceData(
    @SerializedName("id") val id: Int,
    @SerializedName("description") val description: String
)

data class ProjectData(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("link") val link: String,
    @SerializedName("year") val year: String
)

data class AchievementData(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("nomination") val nomination: String,
    @SerializedName("year") val year: String
)

data class InterestData(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
