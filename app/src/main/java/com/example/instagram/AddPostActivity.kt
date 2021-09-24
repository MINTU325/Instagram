package com.example.instagram

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.MOdels.UserPostsModel
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.android.synthetic.main.post_item_layout.*

class AddPostActivity : AppCompatActivity() {

    private var myUrl = ""
    private var ImageUri: Uri? = null
    private var storagePostePicref: StorageReference? = null
    private var firebaseDatabase = FirebaseDatabase.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        storagePostePicref = FirebaseStorage.getInstance().reference.child("User Posts")
        CropImage.activity()
            .setAspectRatio(2, 1)
            .start(this@AddPostActivity)

        btnClose.setOnClickListener {
            startActivity(Intent(this, MainScreenActivity::class.java))
        }

        btnSavePost.setOnClickListener {

            when {
                ImageUri == null -> Toast.makeText(
                    this,
                    "Please select image first.",
                    Toast.LENGTH_LONG
                ).show()
                TextUtils.isEmpty(etDescriptionPost.text.toString()) -> Toast.makeText(
                    this,
                    "Please write full name first.",
                    Toast.LENGTH_LONG
                ).show()
                else -> {
                    val progressDialog = ProgressDialog(this)
                    progressDialog.setTitle("Account Settings")
                    progressDialog.setMessage("Please wait, Post is Uploading...")
                    progressDialog.show()


                    val fileRef =
                        storagePostePicref!!.child(System.currentTimeMillis().toString() + ".jpg")
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
                            val description = etDescriptionPost.text.toString()
                            var publisher: String = ""

                            firebaseDatabase.getReference("users")
                                .child(firebaseAuth.currentUser!!.uid).child("username")
                                .addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        publisher = snapshot.value.toString()

                                        val postDetails =
                                            UserPostsModel(postId, image, description, publisher,)

                                        ref.child(firebaseAuth.currentUser!!.uid).child(postId)
                                            .setValue(postDetails)
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                    }
                                })

                            progressDialog.dismiss()

                            Toast.makeText(this, "Post Uploaded", Toast.LENGTH_LONG).show()

                            val intent =
                                Intent(this@AddPostActivity, MainScreenActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else
                            progressDialog.dismiss()

                    }

                }

            }


        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val result = CropImage.getActivityResult(data)
            ImageUri = result.uri
            ivImagePost.setImageURI(ImageUri)
        }

    }
}