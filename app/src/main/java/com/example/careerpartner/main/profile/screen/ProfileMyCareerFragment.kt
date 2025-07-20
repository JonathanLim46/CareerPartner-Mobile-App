package com.example.careerpartner.main.profile.screen

import android.annotation.SuppressLint
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
import com.example.careerpartner.databinding.FragmentProfileMyCareerBinding
import com.example.careerpartner.main.profile.adapter.ProfileAdapter
import com.example.careerpartner.main.profile.data.ProfileCareerData
import java.text.NumberFormat
import java.util.Locale

class ProfileMyCareerFragment : Fragment() {

    private var _binding: FragmentProfileMyCareerBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProfileAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var profileData: List<ProfileCareerData>
    private lateinit var rawData: List<List<String>>

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

        viewModelUser.userLearningPathsResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Success -> {
                    profileData = it.data?.data?.map {
                        ProfileCareerData(
                            title = it.title,
                            source = it.url
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
                    Toast.makeText(
                        requireContext(),
                        "Something went wrong, please try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupDataRv() {
        adapter = ProfileAdapter(profileData)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }
}