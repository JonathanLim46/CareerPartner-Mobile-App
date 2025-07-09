package com.example.careerpartner.data.repository

import com.example.careerpartner.data.model.UserResponse
import com.example.careerpartner.data.network.UserApi
import okhttp3.Response

class UserRepository {

    suspend fun getTalentData(token: String): retrofit2.Response<UserResponse>?{
        return UserApi.getApi()?.getTalentData(token = token
        )
    }
}