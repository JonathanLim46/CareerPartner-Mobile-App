package com.example.careerpartner.data.viewmodel

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.careerpartner.data.model.UserAchievementsRequest
import com.example.careerpartner.data.model.UserAchievementsResponse
import com.example.careerpartner.data.model.UserEducationRequest
import com.example.careerpartner.data.model.UserEducationResponse
import com.example.careerpartner.data.model.UserInterestsRequest
import com.example.careerpartner.data.model.UserInterestsRespond
import com.example.careerpartner.data.model.UserProjectsResponse
import com.example.careerpartner.data.model.UserResponse
import com.example.careerpartner.data.model.UserSkillsAllRespond
import com.example.careerpartner.data.model.UserSkillsRequest
import com.example.careerpartner.data.model.UserSkillsRespond
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

    val _userAchievementsResult: MutableLiveData<BaseResponse<UserAchievementsResponse>> =
        MutableLiveData()
    val userAchievementResult: LiveData<BaseResponse<UserAchievementsResponse>> =
        _userAchievementsResult

    val _userAddAchievementResult: MutableLiveData<Event<BaseResponse<UserUpdateResponse>>> =
        MutableLiveData()
    val userAddAchievementResult: LiveData<Event<BaseResponse<UserUpdateResponse>>> =
        _userAddAchievementResult

    val _userDeleteAchievementResult: MutableLiveData<Event<BaseResponse<UserUpdateResponse>>> =
        MutableLiveData()
    val userDeleteAchievementResult: LiveData<Event<BaseResponse<UserUpdateResponse>>> =
        _userDeleteAchievementResult

    val _userUpdateAchievementResult: MutableLiveData<Event<BaseResponse<UserUpdateResponse>>> =
        MutableLiveData()
    val userUpdateAchievementResult: LiveData<Event<BaseResponse<UserUpdateResponse>>> =
        _userUpdateAchievementResult

    val _userProjectsResult: MutableLiveData<BaseResponse<UserProjectsResponse>> = MutableLiveData()
    val userProjectsResult: LiveData<BaseResponse<UserProjectsResponse>> = _userProjectsResult

    val _userDeleteProjectResult: MutableLiveData<Event<BaseResponse<UserUpdateResponse>>> =
        MutableLiveData()
    val userDeleteProjectResult: LiveData<Event<BaseResponse<UserUpdateResponse>>> =
        _userDeleteProjectResult

    val _userAddProjectResult: MutableLiveData<Event<BaseResponse<UserUpdateResponse>>> =
        MutableLiveData()
    val userAddProjectResult: LiveData<Event<BaseResponse<UserUpdateResponse>>> =
        _userAddProjectResult

    val _userUpdateProjectResult: MutableLiveData<Event<BaseResponse<UserUpdateResponse>>> =
        MutableLiveData()
    val userUpdateProjectResult: LiveData<Event<BaseResponse<UserUpdateResponse>>> =
        _userUpdateProjectResult

    val _userAddInterestsResult: MutableLiveData<Event<BaseResponse<UserInterestsRespond>>> =
        MutableLiveData()
    val userAddInterestsResult: LiveData<Event<BaseResponse<UserInterestsRespond>>> =
        _userAddInterestsResult

    val _userAddSkillsResult: MutableLiveData<Event<BaseResponse<UserSkillsRespond>>> =
        MutableLiveData()
    val userAddSkillsResult: LiveData<Event<BaseResponse<UserSkillsRespond>>> = _userAddSkillsResult

    val _userGetSkillsResult: MutableLiveData<BaseResponse<UserSkillsAllRespond>> =
        MutableLiveData()
    val userGetSkillsResult: LiveData<BaseResponse<UserSkillsAllRespond>> = _userGetSkillsResult

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
                Log.e(
                    "UPLOAD",
                    "Code: ${responseAdd?.code()}, ErrorBody: ${responseAdd?.errorBody()?.string()}"
                )
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

    // Achievement

    fun getAchievementsData(activity: Activity) {
        viewModelScope.launch {
            val responseAchievement =
                userRepo.getAchievementsData(token = "Bearer ${SessionManager.getToken(activity)}")
            try {
                if (responseAchievement?.code() == 200) {
                    _userAchievementsResult.value = BaseResponse.Success(responseAchievement.body())
                } else {
                    _userAchievementsResult.value =
                        BaseResponse.Error("Check your internet connection")
                }
            } catch (ex: ConnectException) {
                _userAchievementsResult.value =
                    BaseResponse.Error("Unable to connect to the server. Please check your internet connection.")
            } catch (ex: SocketTimeoutException) {
                _userAchievementsResult.value =
                    BaseResponse.Error("Request timed out. Please try again.")
            } catch (e: Exception) {
                _userAchievementsResult.value = BaseResponse.Error("Exception occurred")
            }
        }
    }

    fun addAchievementData(activity: Activity, title: String, nomination: String, year: String) {
        viewModelScope.launch {
            val responseAddAchievement = userRepo.addAchievementData(
                token = "Bearer ${SessionManager.getToken(activity)}",
                achievementRequest = UserAchievementsRequest(
                    title = title,
                    nominationData = nomination,
                    year = year
                )
            )
            try {
                if (responseAddAchievement?.code() == 201) {
                    _userAddAchievementResult.value =
                        Event(BaseResponse.Success(responseAddAchievement.body()))
                } else {
                    _userAddAchievementResult.value =
                        Event(BaseResponse.Error("Check your internet connection"))
                }
            } catch (e: ConnectException) {
                _userAddAchievementResult.value =
                    Event(BaseResponse.Error("Unable to connect to the server. Please check your internet connection."))
            } catch (e: SocketTimeoutException) {
                _userAddAchievementResult.value =
                    Event(BaseResponse.Error("Request timed out. Please try again."))
            } catch (e: Exception) {
                Log.e(
                    "UPLOAD",
                    "Code: ${responseAddAchievement?.code()}, ErrorBody: ${
                        responseAddAchievement?.errorBody()?.string()
                    }"
                )
                _userAddAchievementResult.value =
                    Event(BaseResponse.Error("Exception occurred"))
            }
        }
    }

    fun deleteAchievementData(activity: Activity, id: Int) {
        viewModelScope.launch {
            val responseDeleteAchievement = userRepo.deleteAchievementData(
                token = "Bearer ${SessionManager.getToken(activity)}",
                id = id
            )
            try {
                if (responseDeleteAchievement?.code() == 200) {
                    _userDeleteAchievementResult.value =
                        Event(BaseResponse.Success(responseDeleteAchievement.body()))
                } else {
                    _userDeleteAchievementResult.value =
                        Event(BaseResponse.Error("Check your internet connection"))
                }
            } catch (e: ConnectException) {
                _userDeleteAchievementResult.value =
                    Event(BaseResponse.Error("Unable to connect to the server. Please check your internet connection."))
            } catch (e: SocketTimeoutException) {
                _userDeleteAchievementResult.value =
                    Event(BaseResponse.Error("Request timed out. Please try again."))
            } catch (e: Exception) {
                _userDeleteAchievementResult.value = Event(BaseResponse.Error("Exception occurred"))
            }
        }
    }

    fun updateAchievementData(
        activity: Activity,
        id: Int,
        achievementRequest: UserAchievementsRequest
    ) {
        viewModelScope.launch {
            val responseUpdate = userRepo.updateAchievementData(
                token = "Bearer ${SessionManager.getToken(activity)}",
                id,
                achievementRequest
            )
            try {
                if (responseUpdate?.code() == 200) {
                    _userUpdateAchievementResult.value =
                        Event(BaseResponse.Success(responseUpdate.body()))
                } else {
                    _userUpdateAchievementResult.value =
                        Event(BaseResponse.Error("Check your internet connection"))
                }
            } catch (e: ConnectException) {
                _userUpdateAchievementResult.value =
                    Event(BaseResponse.Error("Unable to connect to the server. Please check your internet connection."))
            } catch (e: SocketTimeoutException) {
                _userUpdateAchievementResult.value =
                    Event(BaseResponse.Error("Request timed out. Please try again."))
            } catch (e: Exception) {
                _userUpdateAchievementResult.value = Event(BaseResponse.Error("Exception occurred"))
            }
        }
    }

    // Projects

    fun getProjectsData(activity: Activity) {
        viewModelScope.launch {
            val responseProjects =
                userRepo.getProjectsData(token = "Bearer ${SessionManager.getToken(activity)}")
            try {
                if (responseProjects?.code() == 200) {
                    _userProjectsResult.value = BaseResponse.Success(responseProjects.body())
                } else {
                    _userProjectsResult.value = BaseResponse.Error("Check your internet connection")
                }
            } catch (ex: ConnectException) {
                _userProjectsResult.value =
                    BaseResponse.Error("Unable to connect to the server. Please check your internet connection.")
            } catch (ex: SocketTimeoutException) {
                _userProjectsResult.value =
                    BaseResponse.Error("Request timed out. Please try again.")
            } catch (e: Exception) {
                _userProjectsResult.value = BaseResponse.Error("Exception occurred")
            }
        }
    }

    fun deleteProjectData(activity: Activity, id: Int) {
        viewModelScope.launch {
            val responseDeleteProject = userRepo.deleteProjectData(
                token = "Bearer ${SessionManager.getToken(activity)}",
                id = id
            )
            try {
                if (responseDeleteProject?.code() == 200) {
                    _userDeleteProjectResult.value =
                        Event(BaseResponse.Success(responseDeleteProject.body()))
                } else {
                    _userDeleteProjectResult.value =
                        Event(BaseResponse.Error("Check your internet connection"))
                }
            } catch (e: ConnectException) {
                _userDeleteProjectResult.value =
                    Event(BaseResponse.Error("Unable to connect to the server. Please check your internet connection."))
            } catch (e: SocketTimeoutException) {
                _userDeleteProjectResult.value =
                    Event(BaseResponse.Error("Request timed out. Please try again."))
            } catch (e: Exception) {
                _userDeleteProjectResult.value = Event(BaseResponse.Error("Exception occurred"))
            }
        }
    }

    fun addProjectData(
        activity: Activity,
        title: String?,
        image: File?,
        link: String?,
        year: String?
    ) {
        viewModelScope.launch {
            val responseAddProject = userRepo.addProjectData(
                token = "Bearer ${SessionManager.getToken(activity)}",
                title = title,
                image = image,
                link = link,
                year = year
            )
            try {
                if (responseAddProject?.code() == 201) {
                    _userAddProjectResult.value =
                        Event(BaseResponse.Success(responseAddProject.body()))
                } else {
                    _userAddProjectResult.value =
                        Event(BaseResponse.Error("Check your internet connection"))
                }
            } catch (e: ConnectException) {
                _userAddProjectResult.value =
                    Event(BaseResponse.Error("Unable to connect to the server. Please check your internet connection."))
            } catch (e: SocketTimeoutException) {
                _userAddProjectResult.value =
                    Event(BaseResponse.Error("Request timed out. Please try again."))
            } catch (e: Exception) {
                _userAddProjectResult.value = Event(BaseResponse.Error("Exception occurred"))
            }
        }
    }

    fun updateProjectData(
        activity: Activity,
        id: Int,
        title: String?,
        image: File?,
        link: String?,
        year: String?
    ) {
        viewModelScope.launch {
            val responseAddProject = userRepo.updateProjectData(
                token = "Bearer ${SessionManager.getToken(activity)}",
                id = id,
                title = title,
                image = image,
                link = link,
                year = year
            )
            try {
                if (responseAddProject?.code() == 200) {
                    _userAddProjectResult.value =
                        Event(BaseResponse.Success(responseAddProject.body()))
                } else {
                    _userAddProjectResult.value =
                        Event(BaseResponse.Error("Check your internet connection"))
                }
            } catch (e: ConnectException) {
                _userAddProjectResult.value =
                    Event(BaseResponse.Error("Unable to connect to the server. Please check your internet connection."))
            } catch (e: SocketTimeoutException) {
                _userAddProjectResult.value =
                    Event(BaseResponse.Error("Request timed out. Please try again."))
            } catch (e: Exception) {
                _userAddProjectResult.value = Event(BaseResponse.Error("Exception occurred"))
            }
        }
    }

    // Interests

    fun addInterestsData(
        activity: Activity,
        interestsRequest: UserInterestsRequest
    ) {
        viewModelScope.launch {
            val responseInterests = userRepo.addInterestsData(
                token = "Bearer ${SessionManager.getToken(activity)}",
                interests = interestsRequest
            )
            try {
                if (responseInterests?.code() == 201) {
                    _userAddInterestsResult.value =
                        Event(BaseResponse.Success(responseInterests.body()))
                } else {
                    _userAddInterestsResult.value =
                        Event(BaseResponse.Error("Check your internet connection"))
                }
            } catch (e: ConnectException) {
                _userAddInterestsResult.value =
                    Event(BaseResponse.Error("Unable to connect to the server. Please check your internet connection."))
            } catch (e: SocketTimeoutException) {
                _userAddInterestsResult.value =
                    Event(BaseResponse.Error("Request timed out. Please try again."))
            } catch (e: Exception) {
                _userAddInterestsResult.value = Event(BaseResponse.Error("Exception occurred"))
            }
        }
    }

    // Skills

    fun addSkillsData(
        activity: Activity,
        skillsRequest: UserSkillsRequest
    ) {
        viewModelScope.launch {
            val responseSkills = userRepo.addSkillsData(
                token = "Bearer ${SessionManager.getToken(activity)}",
                skills = skillsRequest
            )
            try {
                if (responseSkills?.code() == 201) {
                    _userAddSkillsResult.value = Event(BaseResponse.Success(responseSkills.body()))
                } else {
                    _userAddSkillsResult.value =
                        Event(BaseResponse.Error("Check your internet connection"))
                }
            } catch (e: ConnectException) {
                _userAddSkillsResult.value =
                    Event(BaseResponse.Error("Unable to connect to the server. Please check your internet connection."))
            } catch (e: SocketTimeoutException) {
                _userAddSkillsResult.value =
                    Event(BaseResponse.Error("Request timed out. Please try again."))
            } catch (e: Exception) {
                _userAddSkillsResult.value = Event(BaseResponse.Error("Exception occurred"))
            }
        }
    }

    fun getSkillsData(activity: Activity) {
        viewModelScope.launch {
            val responseSkills =
                userRepo.getSkillsData(token = "Bearer ${SessionManager.getToken(activity)}")
            try {
                if (responseSkills?.code() == 200) {
                    _userGetSkillsResult.value = BaseResponse.Success(responseSkills.body())
                } else {
                    _userGetSkillsResult.value =
                        BaseResponse.Error("Check your internet connection")
                }
            } catch (e: ConnectException) {
                _userAddSkillsResult.value =
                    Event(BaseResponse.Error("Unable to connect to the server. Please check your internet connection."))
            } catch (e: SocketTimeoutException) {
                _userAddSkillsResult.value =
                    Event(BaseResponse.Error("Request timed out. Please try again."))
            } catch (e: Exception) {
                _userAddSkillsResult.value = Event(BaseResponse.Error("Exception occurred"))
            }
        }
    }

    fun deleteSkillsData(activity: Activity, id: Int) {
        viewModelScope.launch {
            val response =
                userRepo.deleteSkillData(token = "Bearer ${SessionManager.getToken(activity)}", id = id)
            try {
                if (response?.code() == 200){
                    Log.d("Skills", "Success Delete $id")
                } else {
                    Log.d("Skills", "Failed Delete $id")
                }
            } catch (e: ConnectException) {
                Log.e("Skills". javaClass.name, e.message, e)
            } catch (e: SocketTimeoutException) {
                Log.e("Skills". javaClass.name, e.message, e)
            } catch (e: Exception) {
                Log.e("Skills". javaClass.name, e.message, e)
            }
        }
    }
}