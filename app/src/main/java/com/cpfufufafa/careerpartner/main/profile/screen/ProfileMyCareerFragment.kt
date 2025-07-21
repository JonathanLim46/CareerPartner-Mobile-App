package com.cpfufufafa.careerpartner.main.profile.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cpfufufafa.careerpartner.data.network.BaseResponse
import com.cpfufufafa.careerpartner.data.viewmodel.UserViewModel
import com.cpfufufafa.careerpartner.databinding.FragmentProfileMyCareerBinding
import com.cpfufufafa.careerpartner.main.profile.adapter.ProfileAdapter
import com.cpfufufafa.careerpartner.main.profile.data.ProfileCareerData
import java.text.NumberFormat
import java.util.Locale

class ProfileMyCareerFragment : Fragment() {

    private var _binding: FragmentProfileMyCareerBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProfileAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var profileData: List<ProfileCareerData>

    private val viewModelUser: UserViewModel by activityViewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileMyCareerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvMyCareerPath

        viewModelUser.getTalentData(requireActivity())
        viewModelUser.getLearningPaths(requireActivity())

        observeUserData()
        observeLearningPathsData()
        observeUpdateLearningPath()
        observeGenerateCareer()

        binding.ivRefreshCareer.setOnClickListener {
            viewModelUser.generateProfileCareer(requireActivity())
        }


    }

    private fun setupDataRv() {
        adapter = ProfileAdapter(profileData)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        adapter.onItemClick = {
            var url = it.url.orEmpty()
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://$url"
            }
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            startActivity(intent)
        }

        adapter.onItemDone = {
            if (it.isDone == 0){
                viewModelUser.updateLearningPath(requireActivity(), id = it.id, 1)
            } else {
                viewModelUser.updateLearningPath(requireActivity(), id = it.id, 0)
            }
        }
    }

    private fun observeUserData(){
        viewModelUser.userResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Loading -> {
                    binding.layoutMyCareer.visibility = View.GONE
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.shimmerLayout.startShimmer()
                }

                is BaseResponse.Success -> {
                    val salary =
                        it.data?.data?.talent?.talent?.expectedSalary?.toDoubleOrNull() ?: 0.0
                    val formatSalary =
                        NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(salary)
                    binding.tvMyCareerGoals.text = it.data?.data?.talent?.talent?.goalCareer
                    binding.tvMyCareerSalary.text = "Expected Salary: $formatSalary"
                    binding.tvMyCareerGoalsDesc.text = it.data?.data?.talent?.talent?.description
                    binding.tvMyCareerJobOpportunity.text = "Job Opportunity: ${it.data?.data?.talent?.talent?.jobOpportunity}"
                    binding.layoutMyCareer.visibility = View.VISIBLE
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                }

                is BaseResponse.Error -> {
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Toast.makeText(
                        requireContext(),
                        "Something went wrong, please try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun observeLearningPathsData(){
        viewModelUser.userLearningPathsResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Success -> {
                    profileData = it.data?.data?.map {
                        ProfileCareerData(
                            id = it.id,
                            title = it.title,
                            source = it.source,
                            url = it.url,
                            isDone = it.isDone
                        )
                    } ?: listOf()
                    setupDataRv()
                }

                is BaseResponse.Error -> {
                    profileData = listOf()
                    setupDataRv()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                }

                else -> {
                    profileData = listOf()
                    setupDataRv()
                    Log.e("ProfileMyCareerFragment", "Something went wrong")
                }
            }
        }
    }

    private fun observeUpdateLearningPath(){
        viewModelUser.userUpdateLearningPathsResult.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                when (it) {
                    is BaseResponse.Success -> {
                        Toast.makeText(requireContext(), "Path Course Updated", Toast.LENGTH_SHORT).show()
                        viewModelUser.getLearningPaths(requireActivity())
                    }

                    is BaseResponse.Error -> {
                        Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        Toast.makeText(requireContext(), "Something went wrong, please try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun observeGenerateCareer(){
        viewModelUser.userGenerateCareerResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Success -> {
                    Toast.makeText(requireContext(), "Career Generated", Toast.LENGTH_SHORT).show()
                    viewModelUser.getTalentData(requireActivity())
                    viewModelUser.getLearningPaths(requireActivity())
                }
                is BaseResponse.Error -> {
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Something went wrong, please try again", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}