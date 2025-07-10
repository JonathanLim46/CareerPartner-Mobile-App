package com.example.careerpartner.data.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.careerpartner.data.model.UserEducationResponse
import com.example.careerpartner.data.model.UserResponse
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.network.SessionManager
import com.example.careerpartner.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException

class UserViewModel(application: Application) : AndroidViewModel(application) {
    val userRepo = UserRepository()

    val _userResult: MutableLiveData<BaseResponse<UserResponse>> = MutableLiveData()
    val userResult: LiveData<BaseResponse<UserResponse>> = _userResult

    val _userEducationResult: MutableLiveData<BaseResponse<UserEducationResponse>> =
        MutableLiveData()
    val userEducationResult: LiveData<BaseResponse<UserEducationResponse>> = _userEducationResult

    fun getTalentData(activity: Activity) {
        viewModelScope.launch {
            try {
                val response =
                    userRepo.getTalentData(token = "Bearer ${SessionManager.getToken(activity)}")
                if (response?.code() == 200) {
                    _userResult.value = BaseResponse.Success(response.body())
                } else {
                    _userResult.value = BaseResponse.Error("Check your internet connection")
                }
            } catch (ex: ConnectException) {
                _userResult.value =
                    BaseResponse.Error("Unable to connect to the server. Please check your internet connection.")
            } catch (ex: SocketTimeoutException) {
                _userResult.value = BaseResponse.Error("Request timed out. Please try again.")
            } catch (e: Exception) {
                _userResult.value = BaseResponse.Error("Exception occurred")
            }
        }
    }

    fun getEducationData(activity: Activity) {
        viewModelScope.launch {
            try {
                val responseEducation = userRepo.getEducationData(token = "Bearer ${SessionManager.getToken(activity)}")
                if (responseEducation?.code() == 200) {
                    _userEducationResult.value = BaseResponse.Success(responseEducation.body())
                } else {
                    _userEducationResult.value = BaseResponse.Error("Check your internet connection")
                }
            } catch (e: ConnectException) {
                _userEducationResult.value =
                    BaseResponse.Error("Unable to connect to the server. Please check your internet connection.")
            } catch (e: SocketTimeoutException) {
                _userEducationResult.value = BaseResponse.Error("Request timed out. Please try again.")
            } catch (e: Exception) {
                _userEducationResult.value = BaseResponse.Error("Exception occurred")
            }
        }
    }
}