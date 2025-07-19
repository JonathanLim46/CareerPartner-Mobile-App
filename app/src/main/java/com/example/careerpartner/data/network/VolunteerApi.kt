package com.example.careerpartner.data.network

import com.example.careerpartner.data.model.VolunteerDetailResponse
import com.example.careerpartner.data.model.VolunteerResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface VolunteerApi {

    @GET("api/talent/volunteers")
    suspend fun getVolunteerData(@Header("Authorization") token: String): retrofit2.Response<VolunteerResponse>

    @GET("api/talent/volunteers/{id}")
    suspend fun getVolunteerDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): retrofit2.Response<VolunteerDetailResponse>

    companion object {
        fun getApi(): VolunteerApi? {
            return ApiClient.client?.create(VolunteerApi::class.java)
        }
    }
}