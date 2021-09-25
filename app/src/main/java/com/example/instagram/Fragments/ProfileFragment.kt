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
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.instagram.Models.ListsPassingHelper
import com.example.instagram.R
import com.example.instagram.Models.UserDetailsModel
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    private lateinit var ViewPageerr: ViewPager2
    private lateinit var tabLayout: TabLayout
//    private lateinit var pagerAdapter: ProfileViewPagerAdapter
    private var currentUser : UserDetailsModel = UserDetailsModel()
    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    private var currentUserPostsCount = 0
    private var currentUserFollowingsCount = 0
    private var currentUserFollowersCount = 0

    private val storageReference = FirebaseStorage.getInstance().getReference("User Posts")
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private var ImageUri3: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EditProfile.setOnClickListener {
            Toast.makeText(context, "Edit Profile is not implemented yet", Toast.LENGTH_SHORT).show()
        }
        my_pictures.setOnClickListener{
            Toast.makeText(context, "This is My Picture Section ", Toast.LENGTH_SHORT).show()
        }
        saved_pictures.setOnClickListener {
            Toast.makeText(context, "Here tagged Pictures Will be Shown", Toast.LENGTH_SHORT).show()
        }

        for(i in ListsPassingHelper.userDetailsList){
            if(i.uid == currentUserUid.toString()){
                currentUser = i
                break
            }
        }

        setCurrentUserData()

        myprofile_image.setOnClickListener {
            val intent = CropImage.activity(ImageUri3)
                .getIntent(requireContext())
            startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)

        }
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(5).jpg?alt=media&token=9df2e006-6335-457c-b4bc-17610d64de64")
            .into(imageIV1)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(1).jpg?alt=media&token=69295414-e475-420a-8bf4-77dd27b4109a")
            .into(imageIV2)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(2).jpg?alt=media&token=c7249f98-0a2b-4392-96c0-382b59acf2a1")
            .into(imageIV3)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(3).jpg?alt=media&token=86023396-5b74-486a-a38c-49891f881de2")
            .into(imageIV4)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(4).jpg?alt=media&token=85abd3e8-21c8-4129-be7e-403e9b9dd51a")
            .into(imageIV5)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fdownload%20(1).jpg?alt=media&token=04165875-380a-4796-9a9a-8c5044582251")
            .into(imageIV6)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages.jpg?alt=media&token=15ca848c-b09c-4189-b721-2e864b3f91b8")
            .into(imageIV7)

    }

    // Setting current user data in profile section by checking realtime data from firebase.

    private fun setCurrentUserData(){
        currentUserPostsCount = 0
        currentUserFollowingsCount = 0
        currentUserFollowersCount = 0

        firebaseDatabase.getReference("follows").child(currentUserUid.toString())
            .child("followings").addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(i in snapshot.children){
                        currentUserFollowingsCount++
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        followings.text = currentUserFollowingsCount.toString()

        firebaseDatabase.getReference("follows").child(currentUserUid.toString())
            .child("followers").addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(i in snapshot.children){
                        currentUserFollowersCount++
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        followers.text = currentUserFollowersCount.toString()

        firebaseDatabase.getReference("posts").child(currentUserUid.toString())
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(i in snapshot.children){
                        currentUserPostsCount++
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        totalPosts.text = currentUserPostsCount.toString()

        currentUser.apply {
            profileUsername.text = username
            Glide.with(myprofile_image).load(profileImage).into(myprofile_image)
            ProfileName.text = fullName
            ProfileStatus.text = bio
        }
    }

    // Changing current user profile picture and saving it into firebase storage.

    private fun updateCurrentUserprofileInDatabase(){
        val fileRef = storageReference.child(currentUserUid.toString() + ".jpg")
        var uploadTask: StorageTask<*>
        uploadTask = fileRef.putFile(ImageUri3!!)
        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation fileRef.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUrl = task.result.toString()
                currentUser.profileImage = downloadUrl
                firebaseDatabase.getReference("users").child(currentUserUid.toString())
                    .child("profileImage").setValue(downloadUrl)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val result2 = CropImage.getActivityResult(data)
            ImageUri3 = result2.uri
            myprofile_image.setImageURI(ImageUri3)
            updateCurrentUserprofileInDatabase()
        }
    }
}
