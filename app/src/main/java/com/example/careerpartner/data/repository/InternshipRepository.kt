package com.example.careerpartner.data.repository

import com.example.careerpartner.data.model.InternshipDetailResponse
import com.example.careerpartner.data.model.InternshipsResponse
import com.example.careerpartner.data.network.InternshipApi

class InternshipRepository {

    suspend fun getInternshipsData(token: String): retrofit2.Response<InternshipsResponse>?{
        return InternshipApi.getApi()?.getInternshipsData(token = token)
    }

    suspend fun getInternshipDetail(token: String, id: Int): retrofit2.Response<InternshipDetailResponse>?{
        return InternshipApi.getApi()?.getInternshipDetail(token = token, id = id)
    }
}