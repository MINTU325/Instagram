package com.example.instagram.Fragments

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram.R
import com.example.instagram.Models.UserDetailsModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_search_layout_view_accounts.view.*
import kotlinx.android.synthetic.main.fragment_search_layout_view_random.view.*

class SearchFragmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var firebaseDatabase = FirebaseDatabase.getInstance()
    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid.toString()

    fun setRandomData(imageUrl : String){
        Glide.with(itemView.ivRandom).load(imageUrl).into(itemView.ivRandom)
    }

    fun setAccountsData(userDetailsModel: UserDetailsModel){
        firebaseDatabase.getReference("follows").child(currentUserUid)
                .child("followings").child(userDetailsModel.uid.toString())
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        itemView.btnFollow.apply {
                            text = "following"
                            setTextColor(Color.BLACK)
                            setBackgroundColor(0xdbdbdb)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

        itemView.apply {
            userDetailsModel.apply {
                Glide.with(civImage).load(profileImage).into(civImage)
                tvUsername.text = username
                tvFullName.text = fullName
            }
        }

        itemView.btnFollow.setOnClickListener {
            itemView.btnFollow.apply {
                if(text == "Follow"){
                    text = "following"
                    setTextColor(Color.BLACK)
                    setBackgroundColor(0xdbdbdb)
                    firebaseDatabase.getReference("follows").child(currentUserUid)
                        .child("followings").child(userDetailsModel.uid.toString())
                        .setValue("true")
                    firebaseDatabase.getReference("follows").child(userDetailsModel.uid.toString())
                        .child("followers").child(currentUserUid)
                        .setValue("true")
                }
                else{
                    text = "Follow"
                    setTextColor(Color.WHITE)
                    setBackgroundColor(ContextCompat.getColor(context,R.color.blue))
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