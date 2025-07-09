package com.example.careerpartner.data.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.careerpartner.data.model.UserResponse
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.network.SessionManager
import com.example.careerpartner.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException

class UserViewModel(application: Application): AndroidViewModel(application) {
    val userRepo = UserRepository()

    val _userResult : MutableLiveData<BaseResponse<UserResponse>> = MutableLiveData()
    val userResult : LiveData<BaseResponse<UserResponse>> = _userResult

    fun getTalentData(activity : Activity){
        viewModelScope.launch {
            try {
                val response = userRepo.getTalentData(token = "Bearer ${SessionManager.getToken(activity)}")
                if (response?.code() == 200) {
                    _userResult.value = BaseResponse.Success(response.body())
                } else {
                    _userResult.value = BaseResponse.Error("Check your internet connection")
                }
            } catch (ex: ConnectException){
                _userResult.value = BaseResponse.Error("Unable to connect to the server. Please check your internet connection.")
            } catch (ex: SocketTimeoutException){
                _userResult.value = BaseResponse.Error("Request timed out. Please try again.")
            } catch (e: Exception) {
                _userResult.value = BaseResponse.Error("Exception occurred")
            }
        }
    }
}