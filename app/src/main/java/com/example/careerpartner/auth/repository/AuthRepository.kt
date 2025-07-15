package com.example.careerpartner.auth.repository

import com.example.careerpartner.auth.model.LoginRequest
import com.example.careerpartner.auth.model.LoginResponse
import com.example.careerpartner.auth.model.LogoutResponse
import com.example.careerpartner.auth.model.RegisterRequest
import com.example.careerpartner.auth.model.RegisterResponse
import com.example.careerpartner.data.network.AuthApi
import retrofit2.Response

class AuthRepository {

    suspend fun login(loginRequest: LoginRequest) : Response<LoginResponse>? {
        return AuthApi.getApi()?.login(loginRequest = loginRequest)
    }

    suspend fun logout(token: String) : Response<LogoutResponse>? {
        return AuthApi.getApi()?.logout(token = token)
    }

    suspend fun register(registerRequest: RegisterRequest): Response<RegisterResponse>? {
        return AuthApi.getApi()?.register(registerRequest = registerRequest)
    }
}