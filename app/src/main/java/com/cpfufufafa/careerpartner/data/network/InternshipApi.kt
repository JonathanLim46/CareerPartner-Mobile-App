package com.cpfufufafa.careerpartner.data.network

import com.cpfufufafa.careerpartner.data.model.InternshipDetailResponse
import com.cpfufufafa.careerpartner.data.model.InternshipsResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface InternshipApi {

    @GET("api/talent/internships")
    suspend fun getInternshipsData(@Header("Authorization") token: String): retrofit2.Response<InternshipsResponse>

    @GET("api/talent/internships/{id}")
    suspend fun getInternshipDetail(@Header("Authorization") token: String, @Path(value = "id") id: Int): retrofit2.Response<InternshipDetailResponse>

    companion object{
        fun getApi(): InternshipApi? {
            return ApiClient.client?.create(InternshipApi::class.java)
        }
    }
}