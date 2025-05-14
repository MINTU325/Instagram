package com.example.instagram.Fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.NotificationFragment.NotificationFragmentAdapter
import com.example.instagram.R
import com.example.instagram.Models.UserPostsModel
import com.example.instagram.databinding.FragmentNotificationsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private var list = ArrayList<UserPostsModel>()
    private val databaseReference = FirebaseDatabase.getInstance().getReference("posts")

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout and bind views using View Binding
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetching all posts for notifications from Firebase Realtime Database
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (uid in snapshot.children) {
                    for (postId in uid.children) {
                        val userPostsModel: UserPostsModel =
                            postId.getValue(UserPostsModel::class.java)!!
                        addToList(userPostsModel)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun addToList(userPostsModel: UserPostsModel) {
        list.add(userPostsModel)
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed(Runnable {
            setRecyclerView()
        }, 1000)
    }

    private fun setRecyclerView() {
        list.reverse()
        binding.rvRecyclerViewNotify.adapter = NotificationFragmentAdapter(list)
        binding.rvRecyclerViewNotify.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // To avoid memory leaks, clear the binding reference
    }
}
