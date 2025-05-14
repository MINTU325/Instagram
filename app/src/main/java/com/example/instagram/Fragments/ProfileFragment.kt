package com.example.instagram.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.instagram.R
import com.example.instagram.SharedPrf.MyPreference
import com.example.instagram.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.github.dhaval2404.imagepicker.ImagePicker

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val storageReference = FirebaseStorage.getInstance().getReference("User Posts")
    private var imageUri: Uri? = null
    private lateinit var myPreference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        myPreference = MyPreference(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCurrentUserData()

        binding.EditProfile.setOnClickListener {
            Toast.makeText(context, "Edit Profile is not implemented yet", Toast.LENGTH_SHORT).show()
        }

        // ImagePicker for changing profile image
        binding.myprofileImage.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }
    }

    // Load current user data directly using Firebase and SharedPreferences
    private fun loadCurrentUserData() {
        binding.profileUsername.text = myPreference.getUserName()
        Glide.with(this)
            .load(myPreference.getUserProfileImage())
            .apply(RequestOptions().placeholder(R.drawable.profile_icon))
            .into(binding.myprofileImage)


        // Load counts directly using UID
        firebaseDatabase.getReference("follows").child(currentUserUid.toString())
            .child("followings").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    binding.followings.text = snapshot.childrenCount.toString()
                }

                override fun onCancelled(error: DatabaseError) {}
            })

        firebaseDatabase.getReference("follows").child(currentUserUid.toString())
            .child("followers").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    binding.followers.text = snapshot.childrenCount.toString()
                }

                override fun onCancelled(error: DatabaseError) {}
            })

        firebaseDatabase.getReference("posts").child(currentUserUid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    binding.totalPosts.text = snapshot.childrenCount.toString()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    // Update profile picture in Firebase
    private fun updateCurrentUserProfileImage() {
        val fileRef = storageReference.child("$currentUserUid.jpg")
        imageUri?.let {
            fileRef.putFile(it).continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                fileRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUrl = task.result.toString()
                    firebaseDatabase.getReference("users").child(currentUserUid.toString())
                        .child("profileImage").setValue(downloadUrl)

                    // Save the updated profile image in SharedPreferences
                    myPreference.saveUserDetails(
                        userId = currentUserUid ?: "",
                        userName = myPreference.getUserName() ?: "",
                        profileImage = downloadUrl
                    )

                    // Update UI immediately
                    Glide.with(binding.myprofileImage).load(downloadUrl).into(binding.myprofileImage)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            binding.myprofileImage.setImageURI(imageUri) // Display immediately
            updateCurrentUserProfileImage()
        }
    }
}
