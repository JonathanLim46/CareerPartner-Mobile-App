package com.example.careerpartner.main.profile.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.viewmodel.UserViewModel
import com.example.careerpartner.databinding.FragmentProfileEducationalsBinding
import com.example.careerpartner.main.profile.adapter.ProfileEduAchieveAdapter
import com.example.careerpartner.main.profile.data.ProfileHistoryData

class ProfileEducationalsFragment : Fragment() {

    private var _binding : FragmentProfileEducationalsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by activityViewModels<UserViewModel>()

    private lateinit var adapter : ProfileEduAchieveAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var rawData: List<List<String>>
    private lateinit var educationData: List<ProfileHistoryData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rawData = listOf(
            listOf("Bachelor of Computer Science", "Universitas Indonesia", "2019 - 2023"),
            listOf("Software Engineering Technology", "SMKN 26 Jakarta", "2017 - 2019")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileEducationalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvEducation
        viewModel.userEducationResult.observe(viewLifecycleOwner) {
            when(it) {
                is BaseResponse.Success -> {
                    educationData = it.data?.data?.map { ProfileHistoryData(it.institutionName, it.fieldOfStudy, it.startDate + " - " + it.endDate) } ?: listOf()
                    setupRv()
                }
                is BaseResponse.Error -> {
                    educationData = listOf()
                    setupRv()
                    Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_SHORT).show()
                } else -> {
                    educationData = listOf()
                    setupRv()
                    Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.getEducationData(requireActivity())

    }

    private fun setupRv(){
        adapter = ProfileEduAchieveAdapter(educationData)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        adapter.onItemClick = { item ->
            Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
        }
    }
}