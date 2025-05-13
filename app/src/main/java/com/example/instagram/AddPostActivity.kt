package com.example.instagram

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.Models.UserPostsModel
import com.example.instagram.databinding.ActivityAddPostBinding
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AddPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPostBinding
    private var myUrl = ""
    private var ImageUri: Uri? = null
    private var storagePostePicref: StorageReference? = null
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the View Binding
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Storage reference
        storagePostePicref = FirebaseStorage.getInstance().reference.child("User Posts")

        // Open image picker
        binding.btnSavePost.setOnClickListener {
            ImagePicker.with(this)
                .crop()                    // Crop the image (Optional)
                .compress(1024)            // Compress image (Optional)
                .maxResultSize(1080, 1080) // Maximum size of the image (Optional)
                .start()
        }

        // Close button click listener
        binding.btnClose.setOnClickListener {
            startActivity(Intent(this, MainScreenActivity::class.java))
        }

        // Save post button click listener
        binding.btnSavePost.setOnClickListener {

            when {
                ImageUri == null -> Toast.makeText(
                    this,
                    "Please select image first.",
                    Toast.LENGTH_LONG
                ).show()
                TextUtils.isEmpty(binding.etDescriptionPost.text.toString()) -> Toast.makeText(
                    this,
                    "Please write description first.",
                    Toast.LENGTH_LONG
                ).show()
                else -> {
                    val progressDialog = ProgressDialog(this)
                    progressDialog.setTitle("Account Settings")
                    progressDialog.setMessage("Please wait, Post is Uploading...")
                    progressDialog.show()

                    val fileRef = storagePostePicref!!.child(System.currentTimeMillis().toString() + ".jpg")
                    var uploadTask: StorageTask<*>
                    uploadTask = fileRef.putFile(ImageUri!!)
                    uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                                progressDialog.run { dismiss() }
                            }
                        }
                        return@Continuation fileRef.downloadUrl
                    }).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUrl = task.result
                            val ref = firebaseDatabase.getReference("posts")
                            val postId = ref.push().key.toString()
                            val image = downloadUrl.toString()
                            val description = binding.etDescriptionPost.text.toString()
                            var publisher: String = ""

                            firebaseDatabase.getReference("users")
                                .child(firebaseAuth.currentUser!!.uid).child("username")
                                .addValueEventListener(object : ValueEventListener {


                                    override fun onCancelled(error: DatabaseError) {
                                    }

                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        publisher = snapshot.value.toString()

                                        val postDetails =
                                            UserPostsModel(postId, image, description, publisher)

                                        ref.child(firebaseAuth.currentUser!!.uid).child(postId)
                                            .setValue(postDetails)
                                    }


                                })

                            progressDialog.dismiss()

                            Toast.makeText(this, "Post Uploaded", Toast.LENGTH_LONG).show()

                            val intent = Intent(this@AddPostActivity, MainScreenActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            progressDialog.dismiss()
                        }
                    }
                }
            }
        }
    }

    // Handle the result of the image picker
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            // Get image URI from ImagePicker result
            ImageUri = data.data
            binding.ivImagePost.setImageURI(ImageUri) // Update image preview
        }
    }
}
