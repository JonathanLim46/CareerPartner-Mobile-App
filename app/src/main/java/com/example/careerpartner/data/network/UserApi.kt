package com.example.careerpartner.data.network

import com.example.careerpartner.data.model.UserEducationResponse
import com.example.careerpartner.data.model.UserResponse
import com.example.careerpartner.data.model.UserUpdateResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UserApi {

    @GET("api/talent")
    suspend fun getTalentData(@Header("Authorization") token: String): retrofit2.Response<UserResponse>

    @GET("api/talent/education")
    suspend fun getEducationData(@Header("Authorization") token: String): retrofit2.Response<UserEducationResponse>

    @Multipart
    @POST("/api/talent/user")
    suspend fun updateUserData(
        @Header("Authorization") token: String,
        @Part("username") username: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("phone") phone: RequestBody?,
        @Part("password") password: RequestBody,
        @Part profilePicture: MultipartBody.Part?
    ) : retrofit2.Response<UserUpdateResponse>

    companion object {
        fun getApi(): UserApi? {
            return ApiClient.client?.create(UserApi::class.java)
        }
    }

}