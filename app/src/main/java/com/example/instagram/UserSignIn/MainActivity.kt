package com.example.instagram.UserSignIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.instagram.MainScreenActivity
import com.example.instagram.R
import com.example.instagram.SharedPrf.MyPreference

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            val myPreference = MyPreference(this)
            if (myPreference.getLoginStatus()) {
                // User is logged in, go to MainScreenActivity
                startActivity(Intent(this, MainScreenActivity::class.java))
            } else {
                // User not logged in, go to SignupActivity
                startActivity(Intent(this, SignupActivity::class.java))
            }
            finish()
        }, 2000)
    }
}
