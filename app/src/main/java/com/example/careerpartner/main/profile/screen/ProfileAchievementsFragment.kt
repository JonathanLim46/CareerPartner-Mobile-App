package com.example.careerpartner.main.profile.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.databinding.FragmentProfileAchievementsBinding
import com.example.careerpartner.main.profile.adapter.ProfileEduAchieveAdapter
import com.example.careerpartner.main.profile.data.ProfileHistoryData

class ProfileAchievementsFragment : Fragment() {

    private var _binding: FragmentProfileAchievementsBinding ? = null
    private val binding get() = _binding!!

    private lateinit var adapter : ProfileEduAchieveAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var rawData: List<List<String>>
    private lateinit var educationData: List<ProfileHistoryData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rawData = listOf(
            listOf("National Software Engineering", "Top 10 Semifinalist", "2021"),
            listOf("Certified in Google Cloud Associate Engineer", "Google Certifed", "2017")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileAchievementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvAchievement
        setupRv()
    }

    private fun setupRv(){
        educationData = rawData.map {
            ProfileHistoryData(1,it[0], it[1], it[2])
        }

        adapter = ProfileEduAchieveAdapter(educationData)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }
}