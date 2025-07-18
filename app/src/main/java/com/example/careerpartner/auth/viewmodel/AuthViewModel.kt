package com.example.careerpartner.auth.viewmodel

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.careerpartner.auth.model.LoginRequest
import com.example.careerpartner.auth.model.LoginResponse
import com.example.careerpartner.auth.model.LogoutResponse
import com.example.careerpartner.auth.model.RegisterRequest
import com.example.careerpartner.auth.model.RegisterResponse
import com.example.careerpartner.auth.repository.AuthRepository
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.network.SessionManager
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    val authRepo = AuthRepository()
    val _loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()
    val loginResult: LiveData<BaseResponse<LoginResponse>> = _loginResult

    val _logoutResult: MutableLiveData<BaseResponse<LogoutResponse>> = MutableLiveData()
    val logoutResult: LiveData<BaseResponse<LogoutResponse>> = _logoutResult

    val _registerResult: MutableLiveData<BaseResponse<RegisterResponse>> = MutableLiveData()
    val registerResult: LiveData<BaseResponse<RegisterResponse>> = _registerResult

    fun login(identifier: String, password: String) {

        _loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(
                    identifier = identifier,
                    password = password
                )
                val response = authRepo.login(loginRequest = loginRequest)
                if (response?.code() == 200) {
                    _loginResult.value = BaseResponse.Success(response.body())
                } else {
                    Log.e("LoginViewModel", "Login failed: Invalid credentials")
                    _loginResult.value = BaseResponse.Error("Invalid credentials")
                }
            } catch (ex: ConnectException) {
                _loginResult.value =
                    BaseResponse.Error("Unable to connect to the server. Please check your internet connection.")
            } catch (ex: SocketTimeoutException) {
                _loginResult.value = BaseResponse.Error("Request timed out. Please try again.")
            } catch (ex: Exception) {
                _loginResult.value = BaseResponse.Error("Exception occurred")
            }
        }
    }

    fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            val responseRegister = authRepo.register(registerRequest)
            try {
                if (responseRegister?.code() == 201) {
                    _registerResult.value = BaseResponse.Success(responseRegister.body())
                } else {
                    _registerResult.value = BaseResponse.Error("Register failed")
                }
            } catch (ex: ConnectException) {
                _loginResult.value =
                    BaseResponse.Error("Unable to connect to the server. Please check your internet connection.")
            } catch (ex: SocketTimeoutException) {
                _loginResult.value = BaseResponse.Error("Request timed out. Please try again.")
            } catch (ex: Exception) {
                _loginResult.value = BaseResponse.Error("Exception occurred")
            }
        }
    }

    fun logout(activity: Activity) {
        viewModelScope.launch {
            val responseLogout =
                authRepo.logout(token = "Bearer ${SessionManager.getToken(activity)}")
            if (responseLogout?.code() == 200) {
                _logoutResult.value = BaseResponse.Success(responseLogout.body())
            } else {
                _logoutResult.value = BaseResponse.Error("Logout failed")
            }
        }
    }
}