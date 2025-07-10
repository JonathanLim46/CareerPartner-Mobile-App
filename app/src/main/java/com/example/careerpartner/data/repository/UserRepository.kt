package com.example.careerpartner.data.repository

import com.example.careerpartner.data.model.UserEducationResponse
import com.example.careerpartner.data.model.UserResponse
import com.example.careerpartner.data.model.UserUpdateResponse
import com.example.careerpartner.data.network.UserApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import java.io.File

class UserRepository {

    suspend fun getTalentData(token: String): retrofit2.Response<UserResponse>? {
        return UserApi.getApi()?.getTalentData(
            token = token
        )
    }

    suspend fun getEducationData(token: String): retrofit2.Response<UserEducationResponse>? {
        return UserApi.getApi()?.getEducationData(
            token = token
        )
    }

    suspend fun updateUserData(
        token: String,
        image: File?,
        username: String?,
        email: String?,
        phone: String?,
        password: String
    ): retrofit2.Response<UserUpdateResponse>? {
        val imagePart = image?.let {
            val mediaType = "image/*".toMediaTypeOrNull()
            val imageBody = RequestBody.create(mediaType, it)
            MultipartBody.Part.createFormData("profile_picture", it.name, imageBody)
        }

        val usernamePart = username?.takeIf { it.isNotBlank() }?.let {
            RequestBody.create("text/plain".toMediaTypeOrNull(), it)
        }

        val emailPart = email?.takeIf { it.isNotBlank() }?.let {
            RequestBody.create("text/plain".toMediaTypeOrNull(), it)
        }

        val phonePart = phone?.takeIf { it.isNotBlank() }?.let {
            RequestBody.create("text/plain".toMediaTypeOrNull(), it)
        }
        val passwordPart = RequestBody.create("text/plain".toMediaTypeOrNull(), password)

        return UserApi.getApi()?.updateUserData(
            token = token,
            username = usernamePart,
            email = emailPart,
            phone = phonePart,
            password = passwordPart,
            profilePicture = imagePart
        )
    }
}