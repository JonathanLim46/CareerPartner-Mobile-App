package com.example.careerpartner.data.network

import com.example.careerpartner.data.model.UserEducationResponse
import com.example.careerpartner.data.model.UserResponse
import com.example.careerpartner.data.model.UserEducationRequest
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

    @GET("api/talent")
    suspend fun getTalentData(@Header("Authorization") token: String): retrofit2.Response<UserResponse>

    @GET("api/talent/education")
    suspend fun getEducationData(@Header("Authorization") token: String): retrofit2.Response<UserEducationResponse>

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
    ) : retrofit2.Response<UserUpdateResponse>

    @POST("api/talent/education")
    suspend fun addUserEducation(
        @Header("Authorization") token: String,
        @Body addEducationRequest: UserEducationRequest
    ) : retrofit2.Response<UserUpdateResponse>

    companion object {
        fun getApi(): UserApi? {
            return ApiClient.client?.create(UserApi::class.java)
        }
    }

}