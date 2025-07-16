package com.example.careerpartner.data.repository

import com.example.careerpartner.data.model.VolunteerDetailResponse
import com.example.careerpartner.data.model.VolunteerResponse
import com.example.careerpartner.data.network.VolunteerApi

class VolunteerRepository {

    suspend fun getVolunteerData(token: String): retrofit2.Response<VolunteerResponse>? {
        return VolunteerApi.getApi()?.getVolunteerData(token = token)
    }

    suspend fun getVolunteerDetail(token: String, id: Int): retrofit2.Response<VolunteerDetailResponse>? {
        return VolunteerApi.getApi()?.getVolunteerDetail(token = token, id = id)
    }
}