package com.example.careerpartner.data.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.careerpartner.data.model.VolunteerDetailResponse
import com.example.careerpartner.data.model.VolunteerResponse
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.network.SessionManager
import com.example.careerpartner.data.repository.VolunteerRepository
import com.example.careerpartner.util.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException

class VolunteerViewModel(application: Application) : AndroidViewModel(application) {

    private val volunteerRepo = VolunteerRepository()

    val _getVolunteerDataResult: MutableLiveData<BaseResponse<VolunteerResponse>> =
        MutableLiveData()
    val getVolunteerDataResult: LiveData<BaseResponse<VolunteerResponse>> = _getVolunteerDataResult

    val _getVolunteerDetailResult: MutableLiveData<BaseResponse<VolunteerDetailResponse>> =
        MutableLiveData()
    val getVolunteerDetailResult: LiveData<BaseResponse<VolunteerDetailResponse>> = _getVolunteerDetailResult

    fun getVolunteerData(activity: Activity) {
        _getVolunteerDataResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            delay(500)
            val response =
                volunteerRepo.getVolunteerData(token = "Bearer ${SessionManager.getToken(activity)}")
            try {
                if (response?.code() == 200) {
                    _getVolunteerDataResult.value = BaseResponse.Success(response.body())
                } else {
                    _getVolunteerDataResult.value = BaseResponse.Error(response?.message())
                }
            } catch (e: ConnectException) {
                _getVolunteerDataResult.value =
                    BaseResponse.Error("Unable to connect to the server. Please check your internet connection.")
            } catch (e: SocketTimeoutException) {
                _getVolunteerDataResult.value =
                    BaseResponse.Error("Request timed out. Please try again.")
            } catch (e: Exception) {
                _getVolunteerDataResult.value = BaseResponse.Error("Exception occurred")
            }
        }
    }

    fun getVolunteerDetail(activity: Activity, id: Int) {
        _getVolunteerDetailResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            delay(500)
            val response = volunteerRepo.getVolunteerDetail(
                token = "Bearer ${
                    SessionManager.getToken(activity)
                }", id = id
            )
            try {
                if (response?.code() == 200) {
                    _getVolunteerDetailResult.value = BaseResponse.Success(response.body())
                } else {
                    _getVolunteerDetailResult.value =
                        BaseResponse.Error("Check your internet connection")
                }
            } catch (e: ConnectException) {
                _getVolunteerDetailResult.value =
                    BaseResponse.Error("Unable to connect to the server. Please check your internet connection.")
            } catch (e: SocketTimeoutException) {
                _getVolunteerDetailResult.value =
                    BaseResponse.Error("Request timed out. Please try again.")
            } catch (e: Exception) {
                _getVolunteerDetailResult.value = BaseResponse.Error("Exception occurred")
            }
        }
    }
}