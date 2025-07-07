package com.example.careerpartner.data.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.careerpartner.R
import androidx.core.content.edit

object SessionManager {
    private const val PREF_NAME = "auth_prefs"
    const val USER_TOKEN = "user_token"

    fun saveAuthToken(context: Context, token: String){
        saveString(context, USER_TOKEN, token)
    }

    fun getToken(context: Context): String? {
        return getString(context, USER_TOKEN)
    }

    fun saveString(context: Context, key: String, value: String){
        val prefs: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(key, value).apply()
    }

    fun getString(context: Context, key: String): String? {
        val prefs: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(key, null)
    }

    fun clearData(context: Context){
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().commit()
        Log.d("SessionManager", "SharedPreferences cleared with commit")
    }
}
