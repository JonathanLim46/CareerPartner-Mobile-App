package com.example.careerpartner.data.network

import com.example.careerpartner.data.model.UserAchievementsRequest
import com.example.careerpartner.data.model.UserAchievementsResponse
import com.example.careerpartner.data.model.UserEducationResponse
import com.example.careerpartner.data.model.UserResponse
import com.example.careerpartner.data.model.UserEducationRequest
import com.example.careerpartner.data.model.UserInterestsAllRespond
import com.example.careerpartner.data.model.UserInterestsRequest
import com.example.careerpartner.data.model.UserInterestsResponse
import com.example.careerpartner.data.model.UserProjectsResponse
import com.example.careerpartner.data.model.UserSkillsAllRespond
import com.example.careerpartner.data.model.UserSkillsRequest
import com.example.careerpartner.data.model.UserSkillsResponse
import com.example.careerpartner.data.model.UserUpdateResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface UserApi {


    // User

    @GET("api/talent")
    suspend fun getTalentData(@Header("Authorization") token: String): retrofit2.Response<UserResponse>

    @Multipart
    @POST("api/talent/user")
    suspend fun updateUserData(
        @Header("Authorization") token: String,
        @Part("username") username: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("phone") phone: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part profilePicture: MultipartBody.Part?
    ): retrofit2.Response<UserUpdateResponse>

    // Education

    @GET("api/talent/education")
    suspend fun getEducationData(@Header("Authorization") token: String): retrofit2.Response<UserEducationResponse>

    @POST("api/talent/education")
    suspend fun addUserEducation(
        @Header("Authorization") token: String,
        @Body addEducationRequest: UserEducationRequest
    ): retrofit2.Response<UserUpdateResponse>

    @PUT("api/talent/education/{id}")
    suspend fun updateUserEducation(
        @Header("Authorization") token: String,
        @Path(value = "id") id: Int,
        @Body updateEducationRequest: UserEducationRequest
    ): retrofit2.Response<UserUpdateResponse>

    @DELETE("api/talent/education/{id}")
    suspend fun deleteUserEducation(
        @Header("Authorization") token: String,
        @Path(value = "id") id: Int
    ): retrofit2.Response<UserUpdateResponse>


    // Achievements

    @GET("api/talent/achievements")
    suspend fun getAchievementData(@Header("Authorization") token: String): retrofit2.Response<UserAchievementsResponse>

    @POST("api/talent/achievements")
    suspend fun addAchievementData(
        @Header("Authorization") token: String,
        @Body achievementRequest: UserAchievementsRequest
    ): retrofit2.Response<UserUpdateResponse>

    @PUT("api/talent/achievements/{id}")
    suspend fun updateAchievementData(
        @Header("Authorization") token: String,
        @Path(value = "id") id: Int,
        @Body achievementRequest: UserAchievementsRequest
    ): retrofit2.Response<UserUpdateResponse>

    @DELETE("api/talent/achievements/{id}")
    suspend fun deleteAchievementData(
        @Header("Authorization") token: String,
        @Path(value = "id") id: Int
    ): retrofit2.Response<UserUpdateResponse>

    // Projects

    @GET("api/talent/projects")
    suspend fun getProjectsData(@Header("Authorization") token: String): retrofit2.Response<UserProjectsResponse>

    @Multipart
    @POST("api/talent/projects")
    suspend fun addProjectData(
        @Header("Authorization") token: String,
        @Part("title") title: RequestBody?,
        @Part image: MultipartBody.Part?,
        @Part("link") link: RequestBody?,
        @Part("year") year: RequestBody?
    ): retrofit2.Response<UserUpdateResponse>

    @DELETE("api/talent/projects/{id}")
    suspend fun deleteProjectData(
        @Header("Authorization") token: String,
        @Path(value = "id") id: Int
    ): retrofit2.Response<UserUpdateResponse>

    @Multipart
    @POST("api/talent/projects/{id}")
    suspend fun updateProjectData(
        @Header("Authorization") token: String,
        @Path(value = "id") id: Int,
        @Part("title") title: RequestBody?,
        @Part image: MultipartBody.Part?,
        @Part("link") link: RequestBody?,
        @Part("year") year: RequestBody?
    ): retrofit2.Response<UserUpdateResponse>

    // Interests

    @POST("api/talent/interests")
    suspend fun addInterestsData(
        @Header("Authorization") token: String,
        @Body interests: UserInterestsRequest
    ): retrofit2.Response<UserInterestsResponse>

    @GET("api/talent/interests")
    suspend fun getInterestsData(
        @Header("Authorization") token: String,
    ): retrofit2.Response<UserInterestsAllRespond>

    @DELETE("api/talent/interests/{id}")
    suspend fun deleteInterestData(
        @Header("Authorization") token: String,
        @Path(value = "id") id: Int
    ): retrofit2.Response<UserUpdateResponse>

    // Skills

    @POST("api/talent/skills")
    suspend fun addSkillsData(
        @Header("Authorization") token: String,
        @Body skills: UserSkillsRequest
    ): retrofit2.Response<UserSkillsResponse>

    @GET("api/talent/skills")
    suspend fun getSkillsData(
        @Header("Authorization") token: String,
    ): retrofit2.Response<UserSkillsAllRespond>

    @DELETE("api/talent/skills/{id}")
    suspend fun deleteSkillData(
        @Header("Authorization") token: String,
        @Path(value = "id") id: Int
    ): retrofit2.Response<UserUpdateResponse>

    companion object {
        fun getApi(): UserApi? {
            return ApiClient.client?.create(UserApi::class.java)
        }
    }

}