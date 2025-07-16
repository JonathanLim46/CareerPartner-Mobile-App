package com.example.careerpartner.data.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.careerpartner.data.model.InternshipDetailResponse
import com.example.careerpartner.data.model.InternshipsResponse
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.network.SessionManager
import com.example.careerpartner.data.repository.InternshipRepository
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException

class InternshipViewModel(application: Application) : AndroidViewModel(application) {

    private val internshipRepo = InternshipRepository()

    val _getInternshipsDataResult: MutableLiveData<BaseResponse<InternshipsResponse>> =
        MutableLiveData()
    val getInternshipsDataResult: LiveData<BaseResponse<InternshipsResponse>> =
        _getInternshipsDataResult

    val _getInternshipsDetailResult: MutableLiveData<BaseResponse<InternshipDetailResponse>> =
        MutableLiveData()
    val getInternshipsDetailResult: LiveData<BaseResponse<InternshipDetailResponse>> =
        _getInternshipsDetailResult

    fun getInternshipsData(activity: Activity) {
        viewModelScope.launch {
            val response =
                internshipRepo.getInternshipsData(token = "Bearer ${SessionManager.getToken(activity)}")
            try {
                if (response?.code() == 200) {
                    _getInternshipsDataResult.value = BaseResponse.Success(response.body())
                } else {
                    _getInternshipsDataResult.value =
                        BaseResponse.Error("Check your internet connection")

                }
            } catch (e: ConnectException) {
                _getInternshipsDataResult.value =
                    BaseResponse.Error("Unable to connect to the server. Please check your internet connection.")
            } catch (e: SocketTimeoutException) {
                _getInternshipsDataResult.value =
                    BaseResponse.Error("Request timed out. Please try again.")
            } catch (e: Exception) {
                _getInternshipsDataResult.value = BaseResponse.Error("Exception occurred")
            }
        }
    }

    fun getInternshipDetail(activity: Activity, id: Int) {
        viewModelScope.launch {
            val response = internshipRepo.getInternshipDetail(
                token = "Bearer ${
                    SessionManager.getToken(activity)
                }", id = id
            )
            try {
                if (response?.code() == 200) {
                    _getInternshipsDetailResult.value = BaseResponse.Success(response.body())
                } else {
                    _getInternshipsDetailResult.value =
                        BaseResponse.Error("Check your internet connection")
                }
            } catch (e: ConnectException) {
                _getInternshipsDetailResult.value =
                    BaseResponse.Error("Unable to connect to the server. Please check your internet connection.")
            } catch (e: SocketTimeoutException) {
                _getInternshipsDetailResult.value =
                    BaseResponse.Error("Request timed out. Please try again.")
            } catch (e: Exception) {
                _getInternshipsDetailResult.value = BaseResponse.Error("Exception occurred")
            }
        }
    }

}