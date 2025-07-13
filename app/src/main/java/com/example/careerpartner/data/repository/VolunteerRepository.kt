package com.example.careerpartner.data.repository

import com.example.careerpartner.data.model.VolunteerResponse
import com.example.careerpartner.data.network.VolunteerApi

class VolunteerRepository {

    suspend fun getVolunteerData(token: String): retrofit2.Response<VolunteerResponse>? {
        return VolunteerApi.getApi()?.getVolunteerData(token = token)
    }
}