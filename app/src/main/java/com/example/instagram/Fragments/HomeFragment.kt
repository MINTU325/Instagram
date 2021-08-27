package com.example.instagram.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.instagram.AddPostActivity
import com.example.instagram.HomeFragment.PostAdapter
import com.example.instagram.R
import com.example.instagram.StatusActivity
import com.example.instagram.UserPostsModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference = FirebaseDatabase.getInstance().getReference("posts")
    private var list = ArrayList<UserPostsModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (uid in snapshot.children) {
                    for (postId in uid.children) {
                        var userPostsModel: UserPostsModel =
                            postId.getValue(UserPostsModel::class.java)!!
                        addToList(userPostsModel)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnaddPost.setOnClickListener {
            val intent = Intent(context, AddPostActivity::class.java)
            startActivity(intent)

        }
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(5).jpg?alt=media&token=9df2e006-6335-457c-b4bc-17610d64de64")
            .into(ivimage1)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(1).jpg?alt=media&token=69295414-e475-420a-8bf4-77dd27b4109a")
            .into(ivimage2)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(2).jpg?alt=media&token=c7249f98-0a2b-4392-96c0-382b59acf2a1")
            .into(ivimage3)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(3).jpg?alt=media&token=86023396-5b74-486a-a38c-49891f881de2")
            .into(ivimage4)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(4).jpg?alt=media&token=85abd3e8-21c8-4129-be7e-403e9b9dd51a")
            .into(ivimage5)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fdownload%20(1).jpg?alt=media&token=04165875-380a-4796-9a9a-8c5044582251")
            .into(ivimage6)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages.jpg?alt=media&token=15ca848c-b09c-4189-b721-2e864b3f91b8")
            .into(ivimage7)


        layoutStatus.setOnClickListener {
            val intent = Intent(context, StatusActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed(Runnable {
            setRecyclerView()
        },1000)
    }

    private fun addToList(userPostsModel: UserPostsModel) {
        list.add(userPostsModel)
    }

    private fun setRecyclerView() {
        rvRecyclerView_Home?.adapter = PostAdapter(list)
        rvRecyclerView_Home?.layoutManager = LinearLayoutManager(context)
    }

}