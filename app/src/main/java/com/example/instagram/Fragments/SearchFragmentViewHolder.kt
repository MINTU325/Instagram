package com.example.instagram.Fragments

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram.Models.UserDetailsModel
import com.example.instagram.databinding.FragmentSearchLayoutViewAccountsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var firebaseDatabase = FirebaseDatabase.getInstance()
    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid.toString()

    private val binding = FragmentSearchLayoutViewAccountsBinding.bind(itemView)

    fun setRandomData(imageUrl: String) {
        Glide.with(binding.civImage).load(imageUrl).into(binding.civImage)
    }

    fun setAccountsData(userDetailsModel: UserDetailsModel) {
        firebaseDatabase.getReference("follows").child(currentUserUid)
            .child("followings").child(userDetailsModel.uid.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        binding.btnFollow.apply {
                            text = "following"
                            setTextColor(Color.BLACK)
                            setBackgroundColor(0xdbdbdb)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })

        binding.apply {
            userDetailsModel.apply {
                Glide.with(civImage).load(profileImage).into(civImage)
                tvUsername.text = username
                tvFullName.text = fullName
            }
        }

        binding.btnFollow.setOnClickListener {
            binding.btnFollow.apply {
                if (text == "Follow") {
                    text = "following"
                    setTextColor(Color.BLACK)
                    setBackgroundColor(0xdbdbdb)
                    firebaseDatabase.getReference("follows").child(currentUserUid)
                        .child("followings").child(userDetailsModel.uid.toString())
                        .setValue("true")
                    firebaseDatabase.getReference("follows").child(userDetailsModel.uid.toString())
                        .child("followers").child(currentUserUid)
                        .setValue("true")
                } else {
                    text = "Follow"
                    setTextColor(Color.WHITE)
                    setBackgroundColor(ContextCompat.getColor(context, Color.BLUE))
                    firebaseDatabase.getReference("follows").child(currentUserUid)
                        .child("followings").child(userDetailsModel.uid.toString())
                        .removeValue()
                    firebaseDatabase.getReference("follows").child(userDetailsModel.uid.toString())
                        .child("followers").child(currentUserUid)
                        .removeValue()
                }
            }
        }
    }
}
