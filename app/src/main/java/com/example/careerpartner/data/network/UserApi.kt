package com.example.careerpartner.data.network

import com.example.careerpartner.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {

    @GET("/api/talent")
    suspend fun getTalentData(@Header("Authorization") token: String): retrofit2.Response<UserResponse>

    companion object {
        fun getApi(): UserApi? {
            return ApiClient.client?.create(UserApi::class.java)
        }
    }

}