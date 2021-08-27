package com.example.instagram

import android.os.Bundle
import android.os.PatternMatcher
import android.provider.ContactsContract
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_signup.*
import java.util.regex.Pattern

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.getReference("users")
    private lateinit var navController : NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        tvLogin.setOnClickListener {
            navController.navigate(R.id.action_signupFragment_to_loginFragment2)
        }

        btnSignup.setOnClickListener {

            if(isValid()){
                createAccount()
            }
            else
              Toast.makeText(context,"Please fill up details correctly",Toast.LENGTH_SHORT).show()

        }
    }

    // creation of new user account with the help of firebase authentication

    private fun createAccount() {
        firebaseAuth.createUserWithEmailAndPassword(etEmail.text.toString(),etPassword.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context,"Account created successfully",Toast.LENGTH_SHORT).show()
                    saveDataInDatabase()
                } else {
                    Toast.makeText(context,"User already exist",Toast.LENGTH_SHORT).show()
                }
            }
    }

    // after successful creation of account saving user data into firebase realtime database

    private fun saveDataInDatabase(){
        val fullName = etFullName.text.toString()
        val username = etUserName.text.toString()
        val email = etEmail.text.toString()
        val profileImage = "https://firebasestorage.googleapis.com/v0/b/instagram-18379.appspot." +
                "com/o/User%20Posts%2Fprofile.png?alt=media&token=470d3bcf-97b7-4db2-ba80-efb254d474f1"
        val bio = "Instagram user"
        val uId = firebaseAuth.currentUser!!.uid
        val userDetails = UserDetailsModel(fullName, username, email, profileImage, bio, uId)
        databaseReference.child(uId).setValue(userDetails)
        navController.navigate(R.id.action_signupFragment_to_loginFragment2)
    }

    // checking user input details are valid locally

    private fun isValid(): Boolean {

        if(etFullName.text.toString().length < 2)
            return false
        if(etUserName.text.toString().length < 4)
            return false
        if(etPassword.text.toString().length < 6)
            return false
        if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches())
            return false
        return true
    }

}