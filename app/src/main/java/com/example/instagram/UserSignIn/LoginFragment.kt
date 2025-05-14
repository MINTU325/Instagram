package com.example.instagram.UserSignIn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.instagram.*
import com.example.instagram.Models.ListsPassingHelper
import com.example.instagram.Models.UserDetailsModel
import com.example.instagram.SharedPrf.MyPreference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.instagram.databinding.FragmentLoginBinding

// LoginFragment.kt

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.getReference("users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)
        val myPreference = MyPreference(requireContext())

        // Check if the user is already logged in
        if (myPreference.getLoginStatus()) {
            // User is already logged in, directly go to MainScreenActivity
            startActivity(Intent(context, MainScreenActivity::class.java))
            activity?.finish()
            return
        }

        var email = myPreference.getEmail()
        binding.etEmail.setText(email)

        // Handle signup navigation
        binding.tvSignup.setOnClickListener {
            navController.navigate(R.id.action_loginFragment2_to_signupFragment)
        }

        // Handle login click
        binding.btnLogin.setOnClickListener {
            myPreference.setEmail(binding.etEmail.text.toString())
            if (isValid()) {
                login()
            } else {
                Toast.makeText(context, "Please fill up details correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Login user with emailId and password
    private fun login() {
        firebaseAuth.signInWithEmailAndPassword(
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                saveLoginStatus() // Save login status in SharedPreferences
                getDataFromDatabase()
            } else {
                Toast.makeText(context, "Wrong Credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Save login status in SharedPreferences
    private fun saveLoginStatus() {
        val myPreference = MyPreference(requireContext())
        myPreference.setLoginStatus(true)
    }

    // After successful login, get data of current user from Firebase Realtime Database
    private fun getDataFromDatabase() {
        ListsPassingHelper.userDetailsList.clear()
        val myPreference = MyPreference(requireContext())
        val userId = firebaseAuth.currentUser?.uid ?: ""

        // Fetching only logged-in user details using UID
        databaseReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(UserDetailsModel::class.java)?.let { user ->
                    // Save user details in SharedPreferences
                    myPreference.saveUserDetails(
                        userId = userId,
                        userName = user.fullName ?: "",
                        profileImage = user.profileImage ?: ""
                    )
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, MainScreenActivity::class.java))
                    activity?.finish()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show()
                Log.e("FirebaseError", "Error loading data: ${error.message}")
            }
        })
    }
    // Checking user input details are valid locally
    private fun isValid(): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches())
            return false
        if (binding.etPassword.text.toString().length < 6)
            return false
        return true
    }
}
