package com.example.careerpartner.main.home.screen

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.viewmodel.UserViewModel
import com.example.careerpartner.databinding.FragmentHomeBinding
import com.example.careerpartner.main.home.adapter.HomeAdapter
import com.example.careerpartner.main.home.data.HomeData
import kotlin.div
import kotlin.times

class HomeFragment : Fragment() {

    private var currentProgress = 0
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressBar : ProgressBar
    private lateinit var rvInternship : RecyclerView
    private lateinit var rvVolunteer : RecyclerView
    private lateinit var adapterInternships : HomeAdapter
    private lateinit var adapterVolunteer : HomeAdapter
    private lateinit var internshipsData : List<HomeData>
    private lateinit var volunteerData : List<HomeData>
    private lateinit var rawData : List<List<String>>

    private val viewModel: UserViewModel by activityViewModels<UserViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rawData = listOf(
            listOf("Judul 1", "Deskripsi A"),
            listOf("Judul 2", "Deskripsi B"),
            listOf("Judul 3", "Deskripsi C"),
            listOf("Judul 4", "Deskripsi D"),
            listOf("Judul 5", "Deskripsi E"),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MainNavFragment", "MainNavFragment aktif!")
        progressBar = binding.progressBar
        rvInternship = binding.rvInternship
        rvVolunteer = binding.rvVolunteer


        viewModel.userResult.observe(requireActivity()){
            when(it) {
                is BaseResponse.Success -> {
                    binding.tvHomeHello.text = "Hello, ${it.data?.data?.talent?.full_name}"
                    binding.tvHomeHelloSub.text = "Your future career ${it.data?.data?.talent?.talent?.goalCareer}"
                }
                is BaseResponse.Error -> {
                    Log.d("HomeFragment", it.msg.toString())
                    Toast.makeText(requireActivity(), it.msg.toString(), Toast.LENGTH_SHORT).show()
                } else -> {
                    Toast.makeText(requireActivity(), "Something went wrong, check your internet connection !", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.getTalentData(requireActivity())
        setupPathCourse()
        internshipsData()
        volunteersData()
    }

    private fun internshipsData(){
        internshipsData = rawData.map {
            HomeData(it[0], it[1], R.drawable.dummyimg)
        }
        adapterInternships = HomeAdapter(internshipsData)
        rvInternship.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvInternship.adapter = adapterInternships
    }

    private fun volunteersData(){
        volunteerData = rawData.map {
            HomeData(it[0], it[1], R.drawable.dummyimg)
        }
        adapterVolunteer = HomeAdapter(volunteerData)
        rvVolunteer.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvVolunteer.adapter = adapterVolunteer
    }

    private fun setupPathCourse(){
        val pathCourse = arrayOf("HTML", "CSS", "JS", "PHP", "LARAVEL")
        val currentCourse = "CSS"
        progressBar.max = pathCourse.size
        currentProgress = pathCourse.indexOf(currentCourse) + 1
        progressBar.progress = currentProgress

        val colorResId = when {
            currentProgress <= pathCourse.size / 3 -> R.color.red
            currentProgress <= pathCourse.size * 2 / 3 -> R.color.orange
            else -> R.color.green
        }
        val color = ContextCompat.getColor(requireContext(), colorResId)
        progressBar.progressTintList = ColorStateList.valueOf(color)

        binding.tvProgress.text = "$currentProgress/${progressBar.max} steps"
    }
}