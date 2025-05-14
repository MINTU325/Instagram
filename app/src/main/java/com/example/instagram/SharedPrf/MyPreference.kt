package com.example.instagram.SharedPrf

import android.content.Context





class MyPreference(context: Context) {
    private val PREF_NAME = "user_prefs"
    private val PREF_EMAIL_KEY = "EmailKey"
    private val PREF_LOGIN_STATUS_KEY = "is_logged_in"
    private val PREF_USER_NAME = "user_name"
    private val PREF_USER_ID = "user_id"
    private val PREF_USER_PROFILE_IMAGE = "user_profile_image"

    private val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // Get saved email (default: empty)
    fun getEmail(): String {
        return preference.getString(PREF_EMAIL_KEY, "sainimintu34@gmail.com") ?: ""
    }

    // Save email
    fun setEmail(email: String) {
        preference.edit().putString(PREF_EMAIL_KEY, email).apply()
    }

    // Save login status (true if logged in)
    fun setLoginStatus(isLoggedIn: Boolean) {
        preference.edit().putBoolean(PREF_LOGIN_STATUS_KEY, isLoggedIn).apply()
    }

    // Get login status (default: false)
    fun getLoginStatus(): Boolean {
        return preference.getBoolean(PREF_LOGIN_STATUS_KEY, false)
    }

    // Save user details
    fun saveUserDetails(userId: String, userName: String, profileImage: String?) {
        preference.edit().apply {
            putString(PREF_USER_ID, userId)
            putString(PREF_USER_NAME, userName)
            putString(PREF_USER_PROFILE_IMAGE, profileImage ?: "")
            apply()
        }
    }

    // Get user details
    fun getUserId(): String? = preference.getString(PREF_USER_ID, null)
    fun getUserName(): String? = preference.getString(PREF_USER_NAME, null)
    fun getUserProfileImage(): String? = preference.getString(PREF_USER_PROFILE_IMAGE, null)

    // Clear all preferences (for logout)
    fun clearPreferences() {
        preference.edit().clear().apply()
    }
}
