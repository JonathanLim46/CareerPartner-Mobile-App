package com.example.careerpartner.data.network

import com.example.careerpartner.data.model.VolunteerResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface VolunteerApi {

    @GET("api/talent/volunteer")
    suspend fun getVolunteerData(@Header("Authorization") token: String): retrofit2.Response<VolunteerResponse>

    companion object {
        fun getApi(): VolunteerApi? {
            return ApiClient.client?.create(VolunteerApi::class.java)
        }
    }
}