package com.example.instagram.Fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram.Models.UserDetailsModel
import com.example.instagram.databinding.FragmentSearchLayoutViewAccountsBinding
import com.example.instagram.databinding.FragmentSearchLayoutViewRandomBinding

class SearchFragmentAdapter(
    private var listImage: List<String>,
    private var listUser: List<UserDetailsModel>,
    private val viewId: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewId == 0) {
            val binding = FragmentSearchLayoutViewRandomBinding.inflate(inflater, parent, false)
            RandomViewHolder(binding)
        } else {
            val binding = FragmentSearchLayoutViewAccountsBinding.inflate(inflater, parent, false)
            AccountViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RandomViewHolder -> holder.bind(listImage[position])
            is AccountViewHolder -> holder.bind(listUser[position])
        }
    }

    override fun getItemCount() = if (viewId == 0) listImage.size else listUser.size

    fun updateData(newListImage: List<String>, newListUser: List<UserDetailsModel>) {
        listImage = newListImage
        listUser = newListUser
        notifyDataSetChanged()
    }

    // ViewHolder for Random Images
    inner class RandomViewHolder(private val binding: FragmentSearchLayoutViewRandomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            Glide.with(binding.ivRandom).load(imageUrl).into(binding.ivRandom)
        }
    }

    // ViewHolder for User Accounts
    inner class AccountViewHolder(private val binding: FragmentSearchLayoutViewAccountsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userDetailsModel: UserDetailsModel) {
            with(binding) {
                Glide.with(civImage).load(userDetailsModel.profileImage).into(civImage)
                tvUsername.text = userDetailsModel.username
                tvFullName.text = userDetailsModel.fullName
                setupFollowButton(userDetailsModel)
            }
        }

        private fun setupFollowButton(userDetailsModel: UserDetailsModel) {
            val firebaseDatabase = com.google.firebase.database.FirebaseDatabase.getInstance()
            val currentUserUid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid.toString()

            // Set initial follow status
            firebaseDatabase.getReference("follows")
                .child(currentUserUid)
                .child("followings")
                .child(userDetailsModel.uid.toString())
                .addListenerForSingleValueEvent(object : com.google.firebase.database.ValueEventListener {
                    override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                        if (snapshot.exists()) setFollowingButton() else setFollowButton()
                    }

                    override fun onCancelled(error: com.google.firebase.database.DatabaseError) {}
                })

            // Toggle follow/unfollow
            binding.btnFollow.setOnClickListener {
                toggleFollowStatus(userDetailsModel, currentUserUid, firebaseDatabase)
            }
        }

        private fun toggleFollowStatus(
            userDetailsModel: UserDetailsModel,
            currentUserUid: String,
            firebaseDatabase: com.google.firebase.database.FirebaseDatabase
        ) {
            val isFollowing = binding.btnFollow.text == "Following"
            val userRef = firebaseDatabase.getReference("follows")
            val currentUserFollowings = userRef.child(currentUserUid).child("followings")
            val targetUserFollowers = userRef.child(userDetailsModel.uid.toString()).child("followers")

            if (isFollowing) {
                setFollowButton()
                currentUserFollowings.child(userDetailsModel.uid.toString()).removeValue()
                targetUserFollowers.child(currentUserUid).removeValue()
            } else {
                setFollowingButton()
                currentUserFollowings.child(userDetailsModel.uid.toString()).setValue(true)
                targetUserFollowers.child(currentUserUid).setValue(true)
            }
        }

        private fun setFollowButton() {
            binding.btnFollow.apply {
                text = "Follow"
                setTextColor(android.graphics.Color.WHITE)
                setBackgroundResource(com.example.instagram.R.color.blue)
            }
        }

        private fun setFollowingButton() {
            binding.btnFollow.apply {
                text = "Following"
                setTextColor(android.graphics.Color.BLACK)
                setBackgroundColor(android.graphics.Color.parseColor("#DBDBDB"))
            }
        }
    }
}
