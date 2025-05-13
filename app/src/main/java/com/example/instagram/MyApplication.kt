// MyApplication.kt
package com.example.instagram

import android.app.Application
import com.google.firebase.FirebaseApp

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase here
        FirebaseApp.initializeApp(this)
    }
}
