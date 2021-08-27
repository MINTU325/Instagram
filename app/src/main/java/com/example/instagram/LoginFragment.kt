package com.example.instagram

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.getReference("users")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)

        tvSignup.setOnClickListener {
            navController.navigate(R.id.action_loginFragment2_to_signupFragment)
        }

        btnLogin.setOnClickListener {
            if (isValid())
                login()
            else
                Toast.makeText(context, "Please fill up details correctly", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    // login user with emailId and password

    private fun login() {
        firebaseAuth.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    getDataFromDatabase()
                else
                    Toast.makeText(context, "Wrong Credentials", Toast.LENGTH_SHORT).show()
            }
    }

    // after successful login getting data of current user from firebase realtime database

    private fun getDataFromDatabase() {
        ListsPassingHelper.userDetailsList.clear()
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(userDetails in snapshot.children){
                    ListsPassingHelper.userDetailsList.add(userDetails.getValue(UserDetailsModel::class.java)!!)
                }
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(context,MainScreenActivity::class.java))
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    // checking user input details are valid locally

    private fun isValid(): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches())
            return false
        if (etPassword.text.toString().length < 6)
            return false
        return true
    }

}
