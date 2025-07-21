package com.fufufafaAI.careerpartner.auth.repository

import com.fufufafaAI.careerpartner.auth.model.LoginRequest
import com.fufufafaAI.careerpartner.auth.model.LoginResponse
import com.fufufafaAI.careerpartner.auth.model.LogoutResponse
import com.fufufafaAI.careerpartner.auth.model.RegisterRequest
import com.fufufafaAI.careerpartner.auth.model.RegisterResponse
import com.fufufafaAI.careerpartner.data.network.AuthApi
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