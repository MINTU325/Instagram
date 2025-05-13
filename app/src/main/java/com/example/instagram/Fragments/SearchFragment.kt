package com.example.instagram.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.Models.ListsPassingHelper
import com.example.instagram.Models.UserDetailsModel
import com.example.instagram.R
import com.example.instagram.databinding.FragmentSearchBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var listImage = ListsPassingHelper.postImagesUrl
    private var listUser = ListsPassingHelper.userDetailsList

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchBinding.bind(view) // Initialize ViewBinding

        setRecyclerView(0)

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.ivBack.visibility = View.VISIBLE
                listUser = ArrayList()
                for (i in ListsPassingHelper.userDetailsList) {
                    if (i.fullName?.substring(0, s.toString().length)
                            .equals(s.toString(), ignoreCase = true)) {
                        listUser.add(i)
                    }
                }
                setRecyclerView(1)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.ivBack.setOnClickListener {
            binding.ivBack.visibility = View.GONE
            setRecyclerView(0)
        }
    }

    private fun setUserData(search: String) {
        FirebaseDatabase.getInstance().getReference("users")
            .orderByChild("fullName").startAt(search).endAt("$search\uf8ff")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snapshots in snapshot.children) {
                        listUser.add(snapshots.getValue(UserDetailsModel::class.java)!!)
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun setRecyclerView(viewId: Int) {
        binding.rvRecyclerViewSearch?.adapter = SearchFragmentAdapter(listImage, listUser, viewId)
        if (viewId == 0)
            binding.rvRecyclerViewSearch?.layoutManager = GridLayoutManager(context, 3)
        else
            binding.rvRecyclerViewSearch?.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clean up binding to prevent memory leaks
    }
}
