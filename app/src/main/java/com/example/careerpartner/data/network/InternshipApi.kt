package com.example.careerpartner.data.network

import com.example.careerpartner.data.model.InternshipsResponse
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Header

interface InternshipApi {

    @GET("api/talent/internships")
    suspend fun getInternshipsData(@Header("Authorization") token: String): retrofit2.Response<InternshipsResponse>

    companion object{
        fun getApi(): InternshipApi? {
            return ApiClient.client?.create(InternshipApi::class.java)
        }
    }
}