package com.example.instagram

import android.content.Context

class MyPreference(context: Context) {
    val PREF_EMAIL = "prefEmail"
    val PREF_KEy = "EmailKey"
    val preference = context.getSharedPreferences(PREF_EMAIL, Context.MODE_PRIVATE)

    fun getEmail() : String? {
        return preference.getString(PREF_KEy, "sainimintu34@gmail.com")
    }

    fun setEmail(email: String){
        val editorr = preference.edit()
        editorr.putString(PREF_KEy,email)
        editorr.apply()
    }
}