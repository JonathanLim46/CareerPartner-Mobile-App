package com.example.careerpartner.data.network

import android.content.Context
import android.content.SharedPreferences
import com.example.careerpartner.R
import androidx.core.content.edit

object SessionManager {
    const val USER_TOKEN = "user_token"

    fun saveAuthToken(context: Context, token: String){
        saveString(context, USER_TOKEN, token)
    }

    fun getToken(context: Context): String? {
        return getString(context, USER_TOKEN)
    }

    fun saveString(context: Context, key: String, value: String){
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        prefs.edit {
            putString(key, value)
        }
    }

    fun getString(context: Context, key: String): String? {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getString(this.USER_TOKEN, null)
    }

    fun clearData(context: Context){
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
            .edit {
                clear()
            }
    }

}