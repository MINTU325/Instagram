package com.example.instagram.UserSignIn

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.instagram.Models.UserDetailsModel
import com.example.instagram.R
import com.example.instagram.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private lateinit var binding: FragmentSignupBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.getReference("users")
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize ViewBinding
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        // Handle login navigation
        binding.tvLogin.setOnClickListener {
            navController.navigate(R.id.action_signupFragment_to_loginFragment2)
        }

        // Handle signup button click
        binding.btnSignup.setOnClickListener {
            if (isValid()) {
                saveData()
                createAccount()
            } else {
                Toast.makeText(context, "Please fill up details correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Save data locally (optional, you can add the necessary fields)
    private fun saveData() {
        // Example: Saving email, if required
        // val email = binding.etEmail.text.toString()
    }

    // Create new user account with the help of Firebase Authentication
    private fun createAccount() {
        firebaseAuth.createUserWithEmailAndPassword(
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Account created successfully", Toast.LENGTH_SHORT).show()
                saveDataInDatabase()
            } else {
                Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // After successful account creation, save user data into Firebase Realtime Database
    private fun saveDataInDatabase() {
        val fullName = binding.etFullName.text.toString()
        val username = binding.etUserName.text.toString()
        val email = binding.etEmail.text.toString()
        val profileImage = "https://firebasestorage.googleapis.com/v0/b/instagram-18379.appspot." +
                "com/o/User%20Posts%2Fprofile.png?alt=media&token=470d3bcf-97b7-4db2-ba80-efb254d474f1"
        val bio = "Instagram user"
        val uid = firebaseAuth.currentUser?.uid.toString()

        val userDetails = UserDetailsModel(fullName, username, email, profileImage, bio, uid)
        databaseReference.child(uid).setValue(userDetails)

        navController.navigate(R.id.action_signupFragment_to_loginFragment2)
    }

    // Checking user input details are valid locally
    private fun isValid(): Boolean {
        if (binding.etFullName.text.toString().length < 2) return false
        if (binding.etUserName.text.toString().length < 4) return false
        if (binding.etPassword.text.toString().length < 6) return false
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()) return false
        return true
    }
}
