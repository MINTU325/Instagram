package com.example.instagram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.instagram.Fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainScreenActivity : AppCompatActivity() {


    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    moveFrag(HomeFragment())
                    return@OnNavigationItemSelectedListener true


                }
                R.id.nav_search -> {
                    moveFrag(SearchFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_add_post -> {
                    moveFrag(AddPostFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_notification -> {
                    moveFrag(NotificationsFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_profile -> {
                    moveFrag(ProfileFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }

            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        moveFrag(HomeFragment())

    }

    private fun moveFrag(fragmennt: Fragment){
        val fragmenttrans = supportFragmentManager.beginTransaction()
        fragmenttrans.replace(
            R.id.container, fragmennt)
        fragmenttrans.commit()
    }
}