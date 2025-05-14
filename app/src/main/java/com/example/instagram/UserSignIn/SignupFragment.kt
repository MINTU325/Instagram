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
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        // Initialize Firebase
        if (FirebaseApp.getApps(requireContext()).isEmpty()) {
            FirebaseApp.initializeApp(requireContext())
        }

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("users")

        binding.tvLogin.setOnClickListener {
            navController.navigate(R.id.action_signupFragment_to_loginFragment2)
        }

        binding.btnSignup.setOnClickListener {
            if (isValid()) {
                createAccount()
            } else {
                Toast.makeText(context, "Please fill up details correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createAccount() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Account created successfully", Toast.LENGTH_SHORT).show()
                saveDataInDatabase()
            } else {
                Toast.makeText(context, "User already exists or Error occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveDataInDatabase() {
        val fullName = binding.etFullName.text.toString()
        val username = binding.etUserName.text.toString()
        val email = binding.etEmail.text.toString()
        val profileImage = "https://firebasestorage.googleapis.com/v0/b/instagram-18379.appspot.com/o/User%20Posts%2Fprofile.png?alt=media"
        val bio = "Instagram user"
        val uid = firebaseAuth.currentUser?.uid ?: return

        val userDetails = UserDetailsModel(fullName, username, email, profileImage, bio, uid)
        databaseReference.child(uid).setValue(userDetails).addOnCompleteListener {
            navController.navigate(R.id.action_signupFragment_to_loginFragment2)
        }
    }

    private fun isValid(): Boolean {
        return binding.etFullName.text.toString().length >= 2 &&
                binding.etUserName.text.toString().length >= 4 &&
                binding.etPassword.text.toString().length >= 6 &&
                Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()
    }
}
