package com.example.instagram.Fragments

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram.Models.UserDetailsModel
import com.example.instagram.R
import com.example.instagram.databinding.FragmentSearchLayoutViewAccountsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SearchFragmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = FragmentSearchLayoutViewAccountsBinding.bind(itemView)
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid.toString()

    fun setRandomData(imageUrl: String) {
        Glide.with(binding.civImage).load(imageUrl).into(binding.civImage)
    }

    fun setAccountsData(userDetailsModel: UserDetailsModel) {
        // Display user details
        binding.apply {
            Glide.with(civImage).load(userDetailsModel.profileImage).into(civImage)
            tvUsername.text = userDetailsModel.username
            tvFullName.text = userDetailsModel.fullName
        }

        // Set initial follow status
        updateFollowStatus(userDetailsModel)

        // Handle Follow/Unfollow button click
        binding.btnFollow.setOnClickListener {
            toggleFollowStatus(userDetailsModel)
        }
    }

    private fun updateFollowStatus(userDetailsModel: UserDetailsModel) {
        val followRef = firebaseDatabase.getReference("follows")
            .child(currentUserUid)
            .child("followings")
            .child(userDetailsModel.uid.toString())

        followRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) setFollowingButton() else setFollowButton()
        }
    }

    private fun toggleFollowStatus(userDetailsModel: UserDetailsModel) {
        val followRef = firebaseDatabase.getReference("follows")
            .child(currentUserUid)
            .child("followings")
            .child(userDetailsModel.uid.toString())

        val followerRef = firebaseDatabase.getReference("follows")
            .child(userDetailsModel.uid.toString())
            .child("followers")
            .child(currentUserUid)

        if (binding.btnFollow.text == "Follow") {
            setFollowingButton()
            followRef.setValue(true)
            followerRef.setValue(true)
        } else {
            setFollowButton()
            followRef.removeValue()
            followerRef.removeValue()
        }
    }

    private fun setFollowButton() {
        binding.btnFollow.apply {
            text = "Follow"
            setTextColor(Color.WHITE)
            setBackgroundColor(
                ContextCompat.getColor(binding.root.context, R.color.blue)
            )
        }
    }

    private fun setFollowingButton() {
        binding.btnFollow.apply {
            text = "Following"
            setTextColor(Color.BLACK)
            setBackgroundColor(0xdbdbdb)
        }
    }
}
