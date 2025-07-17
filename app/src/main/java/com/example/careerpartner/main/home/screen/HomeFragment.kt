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
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.careerpartner.R
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.viewmodel.InternshipViewModel
import com.example.careerpartner.data.viewmodel.UserViewModel
import com.example.careerpartner.data.viewmodel.VolunteerViewModel
import com.example.careerpartner.databinding.FragmentHomeBinding
import com.example.careerpartner.main.home.adapter.HomeAdapter
import com.example.careerpartner.main.home.data.HomeData
import kotlin.div
import kotlin.times

class HomeFragment : Fragment() {

    private var currentProgress = 0
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressBar: ProgressBar
    private lateinit var rvInternship: RecyclerView
    private lateinit var rvVolunteer: RecyclerView
    private lateinit var adapterInternships: HomeAdapter
    private lateinit var adapterVolunteer: HomeAdapter
    private lateinit var internshipsData: List<HomeData>
    private lateinit var volunteerData: List<HomeData>

    private val viewModelIntern: InternshipViewModel by activityViewModels<InternshipViewModel>()
    private val viewModelVolunteer: VolunteerViewModel by activityViewModels<VolunteerViewModel>()

    private val viewModel: UserViewModel by activityViewModels<UserViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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


        viewModel.userResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.shimmerUserLayout.startShimmer()
                }
                is BaseResponse.Success -> {
                    binding.contentUserLayout.visibility = View.VISIBLE
                    binding.shimmerUserLayout.stopShimmer()
                    binding.shimmerUserLayout.visibility = View.GONE
                    Log.d("HomeFragment", it.data?.data?.talent?.full_name.toString())
                    val fullName = it.data?.data?.talent?.full_name
                    binding.tvHomeHello.text = if (fullName != null){
                        "Hello, $fullName"
                    } else {
                        "Hello, User"
                    }
                    binding.tvHomeHelloSub.text =
                        "Your future career ${it.data?.data?.talent?.talent?.goalCareer}"
                }

                is BaseResponse.Error -> {
                    Log.d("HomeFragment", it.msg.toString())
                    Toast.makeText(requireActivity(), it.msg.toString(), Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Toast.makeText(
                        requireActivity(),
                        "Something went wrong, check your internet connection !",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModelIntern.getInternshipsDataResult.observe(viewLifecycleOwner) {

            when (it) {
                is BaseResponse.Loading -> {
                    binding.contentInternshipsLayout.visibility = View.INVISIBLE
                    binding.shimmerInternshipsLayout.visibility = View.VISIBLE
                    binding.shimmerInternshipsLayout.startShimmer()
                }
                is BaseResponse.Success -> {
                    internshipsData = it.data?.data?.map {
                        HomeData(
                            id = it.id,
                            title = it.title,
                            subTitle = "Status: ${it.status} | Location ${it.location}",
                            image = it.imageCover,
                            status = it.status
                        )
                    }?.filter { it.status == "open" }?.take(5) ?: listOf()
                    internshipsData()
                    binding.shimmerInternshipsLayout.stopShimmer()
                    binding.shimmerInternshipsLayout.visibility = View.GONE
                    binding.contentInternshipsLayout.visibility = View.VISIBLE
                }

                is BaseResponse.Error -> {
                    Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_SHORT).show()
                    internshipsData = listOf()
                    internshipsData()
                }

                else -> {
                    internshipsData = listOf()
                    internshipsData()
                }
            }
        }

        viewModelVolunteer.getVolunteerDataResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.contentVolunteersLayout.visibility = View.INVISIBLE
                    binding.shimmerVolunteersLayout.visibility = View.VISIBLE
                    binding.shimmerVolunteersLayout.startShimmer()
                }
                is BaseResponse.Success -> {
                    volunteerData = it.data?.data?.map {
                        HomeData(
                            id = it.id,
                            title = it.title,
                            subTitle = it.description,
                            image = it.imageCover,
                            status = it.status
                        )
                    }?.filter { it.status == "open" }?.take(5) ?: listOf()
                    volunteersData()
                    binding.shimmerVolunteersLayout.stopShimmer()
                    binding.shimmerVolunteersLayout.visibility = View.GONE
                    binding.contentVolunteersLayout.visibility = View.VISIBLE
                }

                is BaseResponse.Error -> {
                    Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_SHORT).show()
                    volunteerData = listOf()
                    volunteersData()
                }

                else -> {
                    volunteerData = listOf()
                    volunteersData()
                }
            }
        }

        binding.tvViewMoreIntern.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_discoverFragment)
        }

        binding.tvViewMoreTwo.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_discoverFragment2)
        }

        viewModel.getTalentData(requireActivity())
        viewModelIntern.getInternshipsData(requireActivity())
        viewModelVolunteer.getVolunteerData(requireActivity())
        setupPathCourse()

    }

    private fun internshipsData() {
        adapterInternships = HomeAdapter(requireContext(), internshipsData)
        rvInternship.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvInternship.adapter = adapterInternships

        adapterInternships.onItemClick = { item ->
            val bundle = Bundle().apply {
                putString("detail", "internship")
                putInt("detailID", item.id)
            }
            findNavController().navigate(R.id.action_homeFragment_to_discoverDetailFragment, bundle)
        }

        if (internshipsData.isNotEmpty()){
            binding.tvInternshipsNoData.visibility = View.GONE
        } else {
            binding.rvInternship.visibility = View.INVISIBLE
            binding.tvInternshipsNoData.visibility = View.VISIBLE
        }
    }

    private fun volunteersData() {
        adapterVolunteer = HomeAdapter(requireContext(), volunteerData)
        rvVolunteer.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvVolunteer.adapter = adapterVolunteer

        adapterVolunteer.onItemClick = { item ->
            val bundle = Bundle().apply {
                putString("detail", "volunteer")
                putInt("detailID", item.id)
            }
            findNavController().navigate(R.id.action_homeFragment_to_discoverDetailFragment, bundle)
        }

        if (volunteerData.isNotEmpty()){
            binding.tvVolunteerNoData.visibility = View.GONE
        } else {
            binding.rvVolunteer.visibility = View.INVISIBLE
            binding.tvVolunteerNoData.visibility = View.VISIBLE
        }
    }

    private fun setupPathCourse() {
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