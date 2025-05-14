package com.example.instagram

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.instagram.Models.UserPostsModel
import com.example.instagram.databinding.ActivityAddPostBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask

class AddPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPostBinding
    private var myUrl = ""
    private var ImageUri: Uri? = null
    private var storagePostePicref: StorageReference? = null
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openImagePicker()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storagePostePicref = FirebaseStorage.getInstance().reference.child("User Posts")

        // Open image picker
        binding.btnSavePost.setOnClickListener {
            checkPermissionsAndOpenPicker()
        }

        // Close button click listener
        binding.btnClose.setOnClickListener {
            startActivity(Intent(this, MainScreenActivity::class.java))
            finish()
        }

        // Save post button click listener
        binding.btnSavePost.setOnClickListener {
            uploadPost()
        }
    }

    private fun checkPermissionsAndOpenPicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ (API 33+)
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                openImagePicker()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        } else {
            // Below Android 13
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openImagePicker()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start()
    }

    private fun uploadPost() {
        if (ImageUri == null) {
            Toast.makeText(this, "Please select image first.", Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(binding.etDescriptionPost.text.toString())) {
            Toast.makeText(this, "Please write description first.", Toast.LENGTH_LONG).show()
            return
        }

        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading Post")
        progressDialog.setMessage("Please wait...")
        progressDialog.show()

        val fileRef = storagePostePicref!!.child(System.currentTimeMillis().toString() + ".jpg")
        val uploadTask = fileRef.putFile(ImageUri!!)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            fileRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUrl = task.result
                val ref = firebaseDatabase.getReference("posts")
                val postId = ref.push().key.toString()
                val image = downloadUrl.toString()
                val description = binding.etDescriptionPost.text.toString()
                val publisher = firebaseAuth.currentUser?.uid ?: ""

                val postDetails = UserPostsModel(postId, image, description, publisher)
                ref.child(publisher).child(postId).setValue(postDetails).addOnCompleteListener {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Post Uploaded", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, MainScreenActivity::class.java))
                    finish()
                }
            } else {
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to upload post.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            ImageUri = data.data
            binding.ivImagePost.setImageURI(ImageUri)
        }
    }
}
