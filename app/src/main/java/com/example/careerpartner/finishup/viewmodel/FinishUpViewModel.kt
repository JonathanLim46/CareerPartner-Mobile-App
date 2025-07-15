package com.example.careerpartner.finishup.viewmodel

import android.app.Activity
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.careerpartner.data.model.UserAchievementsRequest
import com.example.careerpartner.data.model.UserEducationRequest
import com.example.careerpartner.data.model.UserInterestsRequest
import com.example.careerpartner.data.model.UserSkillsRequest
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.network.SessionManager
import com.example.careerpartner.data.repository.UserRepository
import com.example.careerpartner.finishup.data.FinishUpData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FinishUpViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepo = UserRepository()

    val finishData = MutableLiveData(FinishUpData())

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _submitCompleted = MutableLiveData<Boolean>()
    val submitCompleted: LiveData<Boolean> = _submitCompleted

    fun updateEducation(data: UserEducationRequest) {
        val currentList = finishData.value?.educationInput ?: emptyList()
        finishData.value = finishData.value?.copy(
            educationInput = currentList + data
        )
    }

    fun updateInterests(data: UserInterestsRequest) {
        val currentList = finishData.value?.interestInput ?: emptyList()
        finishData.value = finishData.value?.copy(
            interestInput = currentList + data
        )
    }

    fun updateSkills(data: UserSkillsRequest) {
        val currentList = finishData.value?.skillInput ?: emptyList()
        finishData.value = finishData.value?.copy(
            skillInput = currentList + data
        )
    }

    fun updateAchievement(data: UserAchievementsRequest) {
        val currentList = finishData.value?.experienceInput ?: emptyList()
        finishData.value = finishData.value?.copy(
            experienceInput = currentList + data
        )
    }

    suspend fun submitEducation(token: String): Int {
        val educationList = finishData.value?.educationInput ?: emptyList()
        var successCount = 0
        for (item in educationList) {
            val response =
                runCatching { userRepo.addUserEducationData(token = token, item) }.getOrNull()
            if (response?.isSuccessful == true) successCount++
        }
        return successCount
    }

    suspend fun submitInterests(token: String): Int {
        val interestsList = finishData.value?.interestInput ?: emptyList()
        var successCount = 0
        for (item in interestsList) {
            val response =
                runCatching { userRepo.addInterestsData(token = token, item) }.getOrNull()
            if (response?.isSuccessful == true) successCount++
        }
        return successCount
    }

    suspend fun submitSkills(token: String): Int {
        val skillsList = finishData.value?.skillInput ?: emptyList()
        var successCount = 0
        for (item in skillsList) {
            val response = runCatching { userRepo.addSkillsData(token = token, item) }.getOrNull()
            if (response?.isSuccessful == true) successCount++
        }
        return successCount
    }

    suspend fun submitExperience(token: String): Int {
        val experienceList = finishData.value?.experienceInput ?: emptyList()
        var successCount = 0
        for (item in experienceList) {
            val response =
                runCatching { userRepo.addAchievementData(token = token, item) }.getOrNull()
            if (response?.isSuccessful == true) successCount++
        }
        return successCount
    }

    fun submitAllData(activity: Activity) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                delay(2000)
                val token = "Bearer ${SessionManager.getToken(activity)}"

                val eduSuccess = submitEducation(token)
                val skillSuccess = submitSkills(token)
                val interestSuccess = submitInterests(token)
                val experienceSuccess = submitExperience(token)

                val totalSuccess = eduSuccess + skillSuccess + interestSuccess + experienceSuccess
                val totalItems = listOfNotNull(
                    finishData.value?.educationInput?.size,
                    finishData.value?.skillInput?.size,
                    finishData.value?.interestInput?.size,
                    finishData.value?.experienceInput?.size
                ).sum()
                if (totalSuccess == totalItems) {
                    Toast.makeText(activity, "Berhasil upload semua data", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        activity,
                        "Sebagian data gagal dikirim ($totalSuccess/$totalItems)",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } finally {
                _isLoading.value = false
                _submitCompleted.value = true
            }
        }
    }

    fun resetSubmitCompleted() {
        _submitCompleted.value = false
    }

}