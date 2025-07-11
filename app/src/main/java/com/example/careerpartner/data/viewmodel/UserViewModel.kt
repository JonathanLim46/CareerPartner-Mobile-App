package com.example.careerpartner.data.viewmodel

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.careerpartner.data.model.UserEducationRequest
import com.example.careerpartner.data.model.UserEducationResponse
import com.example.careerpartner.data.model.UserResponse
import com.example.careerpartner.data.model.UserUpdateResponse
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.network.SessionManager
import com.example.careerpartner.data.repository.UserRepository
import com.example.careerpartner.util.Event
import kotlinx.coroutines.delay
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

    val _userUpdateResult: MutableLiveData<Event<BaseResponse<UserUpdateResponse>>> =
        MutableLiveData()
    val userUpdateResult: LiveData<Event<BaseResponse<UserUpdateResponse>>> = _userUpdateResult

    val _userUpdateEducationResult: MutableLiveData<Event<BaseResponse<UserUpdateResponse>>> =
        MutableLiveData()
    val userUpdateEducationResult: LiveData<Event<BaseResponse<UserUpdateResponse>>> =
        _userUpdateEducationResult

    val _userDeleteEducationResult: MutableLiveData<Event<BaseResponse<UserUpdateResponse>>> =
        MutableLiveData()
    val userDeleteEducationResult: LiveData<Event<BaseResponse<UserUpdateResponse>>> =
        _userDeleteEducationResult

    val _userAddEducationResult: MutableLiveData<Event<BaseResponse<UserUpdateResponse>>> =
        MutableLiveData()
    val userAddEducationResult: LiveData<Event<BaseResponse<UserUpdateResponse>>> =
        _userAddEducationResult

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

    fun updateUserEducationData(
        activity: Activity,
        id: Int,
        institutionName: String,
        fieldOfStudy: String,
        startYear: String,
        endYear: String
    ) {
        viewModelScope.launch {
            val responseUpdate = userRepo.updateUserEducationData(
                token = "Bearer ${SessionManager.getToken(activity)}",
                id = id,
                institutionName = institutionName,
                fieldOfStudy = fieldOfStudy,
                startYear = startYear,
                endYear = endYear
            )
            try {
                if (responseUpdate?.code() == 200) {
                    _userUpdateEducationResult.value =
                        Event(BaseResponse.Success(responseUpdate.body()))
                } else {
                    _userUpdateEducationResult.value =
                        Event(BaseResponse.Error("Check your internet connection"))
                }
            } catch (e: ConnectException) {
                _userUpdateEducationResult.value =
                    Event(BaseResponse.Error("Unable to connect to the server. Please check your internet connection."))
            } catch (e: SocketTimeoutException) {
                _userUpdateEducationResult.value =
                    Event(BaseResponse.Error("Request timed out. Please try again."))
            } catch (e: Exception) {
                Log.e(
                    "UPLOAD",
                    "Code: ${responseUpdate?.code()}, ErrorBody: ${
                        responseUpdate?.errorBody()?.string()
                    }"
                )
                _userUpdateEducationResult.value = Event(BaseResponse.Error("Exception occurred"))
            }
        }
    }

    fun deleteUserEducationData(
        activity: Activity,
        id: Int
    ) {
        viewModelScope.launch {
            val responseDelete = userRepo.deleteUserEducationData(
                token = "Bearer ${SessionManager.getToken(activity)}",
                id = id
            )
            try {
                if (responseDelete?.code() == 200) {
                    _userDeleteEducationResult.value =
                        Event(BaseResponse.Success(responseDelete.body()))
                } else {
                    _userDeleteEducationResult.value =
                        Event(BaseResponse.Error("Check your internet connection"))
                }
            } catch (e: ConnectException) {
                _userDeleteEducationResult.value =
                    Event(BaseResponse.Error("Unable to connect to the server. Please check your internet connection."))
            } catch (e: SocketTimeoutException) {
                _userDeleteEducationResult.value =
                    Event(BaseResponse.Error("Request timed out. Please try again."))
            } catch (e: Exception) {
                Log.e(
                    "UPLOAD",
                    "Code: ${responseDelete?.code()}, ErrorBody: ${
                        responseDelete?.errorBody()?.string()
                    }"
                )
                _userDeleteEducationResult.value = Event(BaseResponse.Error("Exception occurred"))
            }
        }
    }

    fun addUserData(
        activity: Activity,
        institutionName: String,
        fieldOfStudy: String,
        startYear: String,
        endYear: String
    ) {
        viewModelScope.launch {
            val responseAdd = userRepo.addUserEducationData(
                token = "Bearer ${SessionManager.getToken(activity)}",
                addEducation = UserEducationRequest(
                    institutionName = institutionName,
                    fieldOfStudy = fieldOfStudy,
                    startYear = startYear,
                    endYear = endYear
                )
            )
            try {
                if (responseAdd?.code() == 201) {
                    _userAddEducationResult.value =
                        Event(BaseResponse.Success(responseAdd.body()))
                } else {
                    _userAddEducationResult.value =
                        Event(BaseResponse.Error("Check your internet connection"))
                }
            } catch (e: ConnectException) {
                _userAddEducationResult.value =
                    Event(BaseResponse.Error("Unable to connect to the server. Please check your internet connection."))
            } catch (e: SocketTimeoutException) {
                _userAddEducationResult.value =
                    Event(BaseResponse.Error("Request timed out. Please try again."))
            } catch (e: Exception) {
                Log.e("UPLOAD", "Code: ${responseAdd?.code()}, ErrorBody: ${responseAdd?.errorBody()?.string()}")
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
                Log.d(
                    "UPLOAD",
                    "Calling updateUserData() with token=${SessionManager.getToken(activity)}"
                )
                if (response?.code() == 200) {
                    _userUpdateResult.value = Event(BaseResponse.Success(response.body()))
                } else {
                    _userUpdateResult.value =
                        Event(BaseResponse.Error("Check your internet connection"))
                }
            } catch (e: ConnectException) {
                _userUpdateResult.value =
                    Event(BaseResponse.Error("Unable to connect to the server. Please check your internet connection."))
            } catch (e: SocketTimeoutException) {
                _userUpdateResult.value =
                    Event(BaseResponse.Error("Request timed out. Please try again."))
            } catch (e: Exception) {
                Log.e(
                    "UPLOAD",
                    "Code: ${response?.code()}, ErrorBody: ${response?.errorBody()?.string()}"
                )
                _userUpdateResult.value = Event(BaseResponse.Error("Exception occurred"))
            }
        }
    }
}