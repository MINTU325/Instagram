package com.example.instagram.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.instagram.AddPostActivity
import com.example.instagram.HomeFragment.PostAdapter
import com.example.instagram.Models.ListsPassingHelper
import com.example.instagram.Models.UserPostsModel
import com.example.instagram.R
import com.example.instagram.StatusActivity
import com.example.instagram.databinding.FragmentHomeBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private var list = ArrayList<UserPostsModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize View Binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ensure Firebase is initialized before making database calls
        if (FirebaseApp.getApps(requireContext()).isNotEmpty()) {
            firebaseAuth = FirebaseAuth.getInstance()
            databaseReference = FirebaseDatabase.getInstance().getReference("posts")
            fetchPosts()
        } else {
            Toast.makeText(requireContext(), "Firebase not initialized.", Toast.LENGTH_SHORT).show()
        }

        setupClickListeners()
        setupStatusImages()
    }

    // Fetching all posts from Firebase Realtime Database
    private fun fetchPosts() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (uid in snapshot.children) {
                    for (postId in uid.children) {
                        val userPostsModel: UserPostsModel = postId.getValue(UserPostsModel::class.java)!!
                        list.add(userPostsModel)
                        ListsPassingHelper.postImagesUrl.add(userPostsModel.image.toString())
                    }
                }
                list.reverse() // Reverse the list to get latest posts first
                setRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load posts.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Setting up RecyclerView
    private fun setRecyclerView() {
        checkNotNull(binding) { "Binding is null, make sure onCreateView is called before this." }
        binding.rvRecyclerViewHome.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecyclerViewHome.adapter = PostAdapter(list)
    }


    // Setup the status images
    private fun setupStatusImages() {
        val images = listOf(
            "https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(5).jpg?alt=media&token=9df2e006-6335-457c-b4bc-17610d64de64",
            "https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(1).jpg?alt=media&token=69295414-e475-420a-8bf4-77dd27b4109a",
            "https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(2).jpg?alt=media&token=c7249f98-0a2b-4392-96c0-382b59acf2a1",
            "https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(3).jpg?alt=media&token=86023396-5b74-486a-a38c-49891f881de2",
            "https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages%20(4).jpg?alt=media&token=85abd3e8-21c8-4129-be7e-403e9b9dd51a",
            "https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fdownload%20(1).jpg?alt=media&token=04165875-380a-4796-9a9a-8c5044582251",
            "https://firebasestorage.googleapis.com/v0/b/instagram-30de6.appspot.com/o/status%20pic%2Fimages.jpg?alt=media&token=15ca848c-b09c-4189-b721-2e864b3f91b8"
        )

        val imageViews = listOf(
            binding.ivimage1, binding.ivimage2, binding.ivimage3,
            binding.ivimage4, binding.ivimage5, binding.ivimage6, binding.ivimage7
        )

        for (i in images.indices) {
            Glide.with(this).load(images[i]).into(imageViews[i])
        }
    }

    // Setting up Click Listeners
    private fun setupClickListeners() {
        binding.btnMessage.setOnClickListener {
            Toast.makeText(context, "Message Activity is yet to be Implemented", Toast.LENGTH_SHORT).show()
        }

        binding.btnaddPost.setOnClickListener {
            startActivity(Intent(context, AddPostActivity::class.java))
        }

        binding.layoutStatus.setOnClickListener {
            startActivity(Intent(context, StatusActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear the binding to avoid memory leaks
    }
}

//initial commit