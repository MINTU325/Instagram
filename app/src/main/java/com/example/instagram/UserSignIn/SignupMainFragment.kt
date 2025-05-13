package com.example.instagram.UserSignIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.instagram.R
import com.example.instagram.databinding.FragmentSignupMainBinding

class SignupMainFragment : Fragment(R.layout.fragment_signup_main) {

    private lateinit var binding: FragmentSignupMainBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize ViewBinding
        binding = FragmentSignupMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        // Handle signup navigation
        binding.tvSignup.setOnClickListener {
            navController.navigate(R.id.action_signupMainFragment_to_signupFragment)
        }

        // Handle login navigation
        binding.tvLogin.setOnClickListener {
            navController.navigate(R.id.action_signupMainFragment_to_loginFragment2)
        }
    }
}
