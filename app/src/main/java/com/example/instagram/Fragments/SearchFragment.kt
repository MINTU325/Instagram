package com.example.instagram.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.Models.UserDetailsModel
import com.example.instagram.R
import com.example.instagram.databinding.FragmentSearchBinding
import com.google.firebase.database.*
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var listImage = ArrayList<String>()
    private var listUser = ArrayList<UserDetailsModel>()
    private lateinit var searchAdapter: SearchFragmentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        fetchImagesForSearch() // Fetch images directly from Firebase for search
        setupRecyclerView()

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                fetchUsersForSearch(s.toString())  // Trigger search as the text changes
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.ivBack.setOnClickListener {
            binding.etSearch.text.clear()
        }
    }

    private fun fetchUsersForSearch(query: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("users")

        // Using query to filter based on search input
        val queryRef = databaseReference.orderByChild("fullName").startAt(query).endAt(query + "\uf8ff")

        queryRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listUser.clear()  // Clear previous data before adding new results
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(UserDetailsModel::class.java)
                    user?.let { listUser.add(it) }
                }
                searchAdapter.updateData(listImage, listUser)  // Refresh adapter with filtered data
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load users for search.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchImagesForSearch() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("posts")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listImage.clear()  // Clear previous data before adding new images
                for (uid in snapshot.children) {
                    for (postId in uid.children) {
                        val imageUrl = postId.child("image").getValue(String::class.java)
                        imageUrl?.let {
                            listImage.add(it)
                        }
                    }
                }
                searchAdapter.updateData(listImage, listUser)  // Refresh adapter with images
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load images for search.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchFragmentAdapter(listImage, listUser, 1) // Set viewId to 1 for user accounts
        binding.rvRecyclerViewSearch.layoutManager = LinearLayoutManager(context)
        binding.rvRecyclerViewSearch.adapter = searchAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Clean up binding to prevent memory leaks
    }
}
