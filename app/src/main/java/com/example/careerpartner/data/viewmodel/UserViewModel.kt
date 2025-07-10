package com.example.careerpartner.data.viewmodel

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.careerpartner.data.model.UserEducationResponse
import com.example.careerpartner.data.model.UserResponse
import com.example.careerpartner.data.model.UserUpdateResponse
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.network.SessionManager
import com.example.careerpartner.data.repository.UserRepository
import com.example.careerpartner.util.Event
import kotlinx.coroutines.launch
import java.io.File
import java.net.ConnectException
import java.net.SocketTimeoutException

class UserViewModel(application: Application) : AndroidViewModel(application) {
    val userRepo = UserRepository()

    val _userResult: MutableLiveData<BaseResponse<UserResponse>> = MutableLiveData()
    val userResult: LiveData<BaseResponse<UserResponse>> = _userResult

    val _userEducationResult: MutableLiveData<BaseResponse<UserEducationResponse>> =
        MutableLiveData()
    val userEducationResult: LiveData<BaseResponse<UserEducationResponse>> = _userEducationResult

    val _userUpdateResult: MutableLiveData<Event<BaseResponse<UserUpdateResponse>>> = MutableLiveData()
    val userUpdateResult: LiveData<Event<BaseResponse<UserUpdateResponse>>> = _userUpdateResult

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
                val responseEducation =
                    userRepo.getEducationData(token = "Bearer ${SessionManager.getToken(activity)}")
                if (responseEducation?.code() == 200) {
                    _userEducationResult.value = BaseResponse.Success(responseEducation.body())
                } else {
                    _userEducationResult.value =
                        BaseResponse.Error("Check your internet connection")
                }
            } catch (e: ConnectException) {
                _userEducationResult.value =
                    BaseResponse.Error("Unable to connect to the server. Please check your internet connection.")
            } catch (e: SocketTimeoutException) {
                _userEducationResult.value =
                    BaseResponse.Error("Request timed out. Please try again.")
            } catch (e: Exception) {
                _userEducationResult.value = BaseResponse.Error("Exception occurred")
            }
        }
    }

    fun updateUserData(
        activity: Activity,
        image: File?,
        username: String,
        email: String,
        phone: String?,
        password: String
    ) {
        viewModelScope.launch {
            val response = userRepo.updateUserData(
                token = "Bearer ${SessionManager.getToken(activity)}",
                image,
                username,
                email,
                phone,
                password
            )
            try {
                Log.d("UPLOAD", "Calling updateUserData() with token=${SessionManager.getToken(activity)}")
                if (response?.code() == 200){
                    _userUpdateResult.value = Event(BaseResponse.Success(response.body()))
                } else {
                    _userUpdateResult.value = Event(BaseResponse.Error("Check your internet connection"))
                }
            } catch (e: ConnectException) {
                _userUpdateResult.value =
                    Event(BaseResponse.Error("Unable to connect to the server. Please check your internet connection."))
            } catch (e: SocketTimeoutException) {
                _userUpdateResult.value = Event(BaseResponse.Error("Request timed out. Please try again."))
            } catch (e: Exception) {
                Log.e("UPLOAD", "Code: ${response?.code()}, ErrorBody: ${response?.errorBody()?.string()}")
                _userUpdateResult.value = Event(BaseResponse.Error("Exception occurred"))
            }
        }
    }
}