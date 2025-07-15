package com.example.careerpartner.data.repository

import com.example.careerpartner.data.model.UserAchievementsRequest
import com.example.careerpartner.data.model.UserAchievementsResponse
import com.example.careerpartner.data.model.UserEducationResponse
import com.example.careerpartner.data.model.UserResponse
import com.example.careerpartner.data.model.UserEducationRequest
import com.example.careerpartner.data.model.UserInterestsRequest
import com.example.careerpartner.data.model.UserInterestsRespond
import com.example.careerpartner.data.model.UserProjectsResponse
import com.example.careerpartner.data.model.UserSkillsRequest
import com.example.careerpartner.data.model.UserSkillsRespond
import com.example.careerpartner.data.model.UserUpdateResponse
import com.example.careerpartner.data.network.UserApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UserRepository {

    // User

    suspend fun getTalentData(token: String): retrofit2.Response<UserResponse>? {
        return UserApi.getApi()?.getTalentData(
            token = token
        )
    }

    suspend fun updateUserData(
        token: String,
        image: File?,
        username: String?,
        email: String?,
        phone: String?,
        password: String?
    ): retrofit2.Response<UserUpdateResponse>? {
        val imagePart = image?.let {
            val mediaType = "image/*".toMediaTypeOrNull()
            val imageBody = RequestBody.create(mediaType, it)
            MultipartBody.Part.createFormData("profile_picture", it.name, imageBody)
        }

        val usernamePart = username?.takeIf { it.isNotBlank() }?.let {
            RequestBody.create("text/plain".toMediaTypeOrNull(), it)
        }

        val emailPart = email?.takeIf { it.isNotBlank() }?.let {
            RequestBody.create("text/plain".toMediaTypeOrNull(), it)
        }

        val phonePart = phone?.takeIf { it.isNotBlank() }?.let {
            RequestBody.create("text/plain".toMediaTypeOrNull(), it)
        }
        val passwordPart = phone?.takeIf { it.isNotBlank() }?.let {
            RequestBody.create("text/plain".toMediaTypeOrNull(), it)
        }

        return UserApi.getApi()?.updateUserData(
            token = token,
            username = usernamePart,
            email = emailPart,
            phone = phonePart,
            password = passwordPart,
            profilePicture = imagePart
        )
    }

    // Education

    suspend fun addUserEducationData(
        token: String,
        addEducation: UserEducationRequest
    ): retrofit2.Response<UserUpdateResponse>? {
        return UserApi.getApi()?.addUserEducation(token = token, addEducation)
    }

    suspend fun getEducationData(token: String): retrofit2.Response<UserEducationResponse>? {
        return UserApi.getApi()?.getEducationData(
            token = token
        )
    }

    suspend fun updateUserEducationData(
        token: String,
        id: Int,
        institutionName: String,
        fieldOfStudy: String,
        startYear: String,
        endYear: String
    ): retrofit2.Response<UserUpdateResponse>? {
        return UserApi.getApi()?.updateUserEducation(
            token = token,
            id = id,
            updateEducationRequest = UserEducationRequest(
                institutionName = institutionName,
                fieldOfStudy = fieldOfStudy,
                startYear = startYear,
                endYear = endYear
            )
        )
    }

    suspend fun deleteUserEducationData(
        token: String,
        id: Int
    ): retrofit2.Response<UserUpdateResponse>? {
        return UserApi.getApi()?.deleteUserEducation(token = token, id = id)
    }

    // Achievements

    suspend fun getAchievementsData(token: String): retrofit2.Response<UserAchievementsResponse>? {
        return UserApi.getApi()?.getAchievementData(
            token = token
        )
    }

    suspend fun addAchievementData(
        token: String,
        achievementRequest: UserAchievementsRequest
    ): retrofit2.Response<UserUpdateResponse>? {
        return UserApi.getApi()
            ?.addAchievementData(token = token, achievementRequest = achievementRequest)
    }

    suspend fun deleteAchievementData(
        token: String,
        id: Int
    ): retrofit2.Response<UserUpdateResponse>? {
        return UserApi.getApi()?.deleteAchievementData(token = token, id = id)
    }

    suspend fun updateAchievementData(
        token: String,
        id: Int,
        achievementsRequest: UserAchievementsRequest
    ): retrofit2.Response<UserUpdateResponse>? {
        return UserApi.getApi()?.updateAchievementData(
            token = token,
            id = id,
            achievementRequest = achievementsRequest
        )
    }

    // Projects

    suspend fun getProjectsData(token: String): retrofit2.Response<UserProjectsResponse>? {
        return UserApi.getApi()?.getProjectsData(token = token)
    }

    suspend fun deleteProjectData(token: String, id: Int): retrofit2.Response<UserUpdateResponse>? {
        return UserApi.getApi()?.deleteProjectData(token = token, id = id)
    }

    suspend fun addProjectData(
        token: String,
        title: String?,
        image: File?,
        link: String?,
        year: String?
    ): retrofit2.Response<UserUpdateResponse>? {
        val imagePart = image?.let {
            val mediaType = "image/*".toMediaTypeOrNull()
            val imageBody = RequestBody.create(mediaType, it)
            MultipartBody.Part.createFormData("image", it.name, imageBody)
        }

        val titlePart = title?.takeIf { it.isNotBlank() }?.let {
            RequestBody.create("text/plain".toMediaTypeOrNull(), it)
        }

        val linkPart = link?.takeIf { it.isNotBlank() }?.let {
            RequestBody.create("text/plain".toMediaTypeOrNull(), it)
        }

        val yearPart = year?.takeIf { it.isNotBlank() }?.let {
            RequestBody.create("text/plain".toMediaTypeOrNull(), it)
        }

        return UserApi.getApi()?.addProjectData(
            token = token,
            title = titlePart,
            image = imagePart,
            link = linkPart,
            year = yearPart
        )
    }

    suspend fun updateProjectData(
        token: String,
        id: Int,
        title: String?,
        image: File?,
        link: String?,
        year: String?
    ): retrofit2.Response<UserUpdateResponse>? {
        val imagePart = image?.let {
            val mediaType = "image/*".toMediaTypeOrNull()
            val imageBody = RequestBody.create(mediaType, it)
            MultipartBody.Part.createFormData("image", it.name, imageBody)
        }

        val titlePart = title?.takeIf { it.isNotBlank() }?.let {
            RequestBody.create("text/plain".toMediaTypeOrNull(), it)
        }

        val linkPart = link?.takeIf { it.isNotBlank() }?.let {
            RequestBody.create("text/plain".toMediaTypeOrNull(), it)
        }

        val yearPart = year?.takeIf { it.isNotBlank() }?.let {
            RequestBody.create("text/plain".toMediaTypeOrNull(), it)
        }

        return UserApi.getApi()?.updateProjectData(
            id = id,
            token = token,
            title = titlePart,
            image = imagePart,
            link = linkPart,
            year = yearPart
        )
    }

    // Interests

    suspend fun addInterestsData(token: String, interests: UserInterestsRequest): retrofit2.Response<UserInterestsRespond>? {
        return UserApi.getApi()?.addInterestsData(token = token, interests = interests)
    }

    // Skills

    suspend fun addSkillsData(token: String, skills: UserSkillsRequest): retrofit2.Response<UserSkillsRespond>? {
        return UserApi.getApi()?.addSkillsData(token = token, skills = skills)
    }
}