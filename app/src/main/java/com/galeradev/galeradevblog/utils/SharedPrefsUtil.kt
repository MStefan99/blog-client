package com.galeradev.galeradevblog.utils

import android.app.Activity
import android.content.SharedPreferences
import com.galeradev.galeradevblog.exceptions.NoSuchCookieException

class SharedPrefsUtil (activity: Activity) {

    private val preferences: SharedPreferences
    private val preferencesEditor: SharedPreferences.Editor

    init {
        preferences = activity.getSharedPreferences(PREF, Activity.MODE_PRIVATE)
        preferencesEditor = preferences.edit()
    }

    fun saveCookie(cookieName: String, cookieValue: String) {
        preferencesEditor.putString(cookieName, cookieValue)
        preferencesEditor.commit()
    }

    fun loadCookie(cookieName: String): String {
        preferences.getString(cookieName, "")?.let{
            return it
        }
        throw NoSuchCookieException("No cookie with name $cookieName saved")
    }


    companion object {
        private const val PREF = "Preferences"
    }
}
