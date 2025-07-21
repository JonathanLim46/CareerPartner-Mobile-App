package com.cpfufufafa.careerpartner.data.network

import com.cpfufufafa.careerpartner.auth.model.LoginRequest
import com.cpfufufafa.careerpartner.auth.model.LoginResponse
import com.cpfufufafa.careerpartner.auth.model.LogoutResponse
import com.cpfufufafa.careerpartner.auth.model.RegisterRequest
import com.cpfufufafa.careerpartner.auth.model.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("api/login")
    suspend fun login(@Body loginRequest: LoginRequest) : retrofit2.Response<LoginResponse>

    @POST("api/logout")
    suspend fun logout(@Header ("Authorization") token: String) : retrofit2.Response<LogoutResponse>

    @POST("api/register")
    suspend fun register(@Body registerRequest: RegisterRequest) : retrofit2.Response<RegisterResponse>

    companion object {
        fun getApi(): AuthApi? {
            return ApiClient.client?.create(AuthApi::class.java)
        }
    }
}