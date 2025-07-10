package com.example.careerpartner.data.network

import com.example.careerpartner.auth.model.LoginRequest
import com.example.careerpartner.auth.model.LoginResponse
import com.example.careerpartner.auth.model.LogoutResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("api/login")
    suspend fun login(@Body loginRequest: LoginRequest) : retrofit2.Response<LoginResponse>

    @POST("api/logout")
    suspend fun logout(@Header ("Authorization") token: String) : retrofit2.Response<LogoutResponse>

    companion object {
        fun getApi(): AuthApi? {
            return ApiClient.client?.create(AuthApi::class.java)
        }
    }
}