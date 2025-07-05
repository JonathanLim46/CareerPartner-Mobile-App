package com.example.careerpartner.auth.viewmodel

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.careerpartner.auth.model.LoginRequest
import com.example.careerpartner.auth.model.LoginResponse
import com.example.careerpartner.auth.model.LogoutResponse
import com.example.careerpartner.auth.repository.AuthRepository
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.network.SessionManager
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException

class LoginViewModel(application: Application): AndroidViewModel(application) {

    val authRepo = AuthRepository()
    val loginResult : MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()
    val logoutResult : MutableLiveData<BaseResponse<LogoutResponse>> = MutableLiveData()

    fun login(identifier: String, password: String){

        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(
                    identifier = identifier,
                    password = password
                )
                val response = authRepo.login(loginRequest = loginRequest)
                if (response?.code() == 200) {
                    loginResult.value = BaseResponse.Success(response.body())
                } else {
                    val errorMsg = response?.errorBody()?.string() ?: "Invalid credentials"
                    Log.e("LoginViewModel", "Login failed: $errorMsg")
                    loginResult.value = BaseResponse.Error(errorMsg)
                }
            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error("Exception occurred")
            } catch (ex: ConnectException){
                loginResult.value = BaseResponse.Error("Unable to connect to the server. Please check your internet connection.")
            } catch (ex: SocketTimeoutException){
                loginResult.value = BaseResponse.Error("Request timed out. Please try again.")
            }
        }
    }

    fun logout(activity: Activity){
        viewModelScope.launch {
            val responseLogout = authRepo.logout(token = "Bearer ${SessionManager.getToken(activity)}")
            if (responseLogout?.code() == 200){
                logoutResult.value = BaseResponse.Success(responseLogout.body())
            } else {
                logoutResult.value = BaseResponse.Error("Logout failed")
            }
        }
    }
}