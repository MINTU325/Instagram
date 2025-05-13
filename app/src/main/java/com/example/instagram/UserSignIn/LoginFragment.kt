package com.example.instagram.UserSignIn

import android.content.Intent
import android.os.Bundle
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

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.getReference("users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize ViewBinding
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)
        val myPreference = MyPreference(requireContext())
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
        firebaseAuth.signInWithEmailAndPassword(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    getDataFromDatabase()
                } else {
                    Toast.makeText(context, "Wrong Credentials", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // After successful login, get data of current user from Firebase Realtime Database
    private fun getDataFromDatabase() {
        ListsPassingHelper.userDetailsList.clear()

        // Use single value event for one-time data fetching instead of continuously listening to changes
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userDetails in snapshot.children) {
                    // Handle possible null values safely
                    userDetails.getValue(UserDetailsModel::class.java)?.let { user ->
                        ListsPassingHelper.userDetailsList.add(user)
                    }
                }

                // Show a success message and navigate to MainScreenActivity
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(context, MainScreenActivity::class.java))
                activity?.finish() // Optionally finish the current activity
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Checking user input details are valid locally
    private fun isValid(): Boolean {
        // Check if email format is valid
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches())
            return false
        // Check if password is at least 6 characters long
        if (binding.etPassword.text.toString().length < 6)
            return false
        return true
    }
}
