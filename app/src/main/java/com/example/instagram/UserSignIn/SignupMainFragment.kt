package com.example.instagram.UserSignIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation
import com.example.instagram.R
import kotlinx.android.synthetic.main.fragment_signup_main.*


class SignupMainFragment : Fragment(R.layout.fragment_signup_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)

        tvSignup.setOnClickListener {
            navController.navigate(R.id.action_signupMainFragment_to_signupFragment)
        }

        tvLogin.setOnClickListener {
            navController.navigate(R.id.action_signupMainFragment_to_loginFragment2)
        }
    }

}