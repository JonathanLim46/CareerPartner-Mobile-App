package com.example.careerpartner.main.profile.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.databinding.FragmentProfileMyCareerBinding
import com.example.careerpartner.main.profile.adapter.ProfileAdapter
import com.example.careerpartner.main.profile.data.ProfileCareerData

class ProfileMyCareerFragment : Fragment() {

    private var _binding: FragmentProfileMyCareerBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProfileAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var profileData: List<ProfileCareerData>
    private lateinit var rawData: List<List<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rawData = listOf(
            listOf("Html Tutorial - Full Course for Beginners", "Udemy"),
            listOf("JS Tutorial - Full Course for Beginners", "Udemy"),
            listOf("CSS Tutorial - Full Course for Beginners", "Udemy")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileMyCareerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvMyCareerPath
        setupDataRv()
    }

    private fun setupDataRv(){
        profileData = rawData.map {
            ProfileCareerData(it[0], it[1])
        }
        adapter = ProfileAdapter(profileData)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }
}