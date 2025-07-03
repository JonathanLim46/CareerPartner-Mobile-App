package com.example.careerpartner.main.profile.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.databinding.FragmentProfileProjectsBinding
import com.example.careerpartner.main.profile.adapter.ProfileProjectAdapter
import com.example.careerpartner.main.profile.data.ProfileProjectsData

class ProfileProjectsFragment : Fragment() {

    private var _binding: FragmentProfileProjectsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProfileProjectAdapter
    private lateinit var projectsData: MutableList<ProfileProjectsData>
    private lateinit var recyclerView: RecyclerView
    private lateinit var rawData: List<List<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rawData = listOf(
            listOf("Love Your Pets Web", "2024"),
            listOf("VetLink Mobile App", "2024"),
            listOf("Fruit Clash Mobile App", "2024"),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileProjectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvProjects
        setupRv()
    }

    private fun setupRv(){
        projectsData = rawData.map {
            ProfileProjectsData(it[0], it[1], R.drawable.dummyimg)
        }.toMutableList()

        adapter = ProfileProjectAdapter(projectsData)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

    }
}