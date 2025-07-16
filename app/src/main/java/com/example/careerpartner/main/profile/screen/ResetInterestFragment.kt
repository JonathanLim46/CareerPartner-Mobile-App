package com.example.careerpartner.main.profile.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.careerpartner.R
import com.example.careerpartner.data.model.Interest
import com.example.careerpartner.data.model.InterestName
import com.example.careerpartner.data.model.Skill
import com.example.careerpartner.data.model.SkillName
import com.example.careerpartner.data.model.UserInterestsRequest
import com.example.careerpartner.data.model.UserSkillsRequest
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.viewmodel.UserViewModel
import com.example.careerpartner.databinding.FragmentResetInterestBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlin.collections.map
import kotlin.collections.toMutableList
import kotlin.getValue


class ResetInterestFragment : Fragment() {

    private var _binding: FragmentResetInterestBinding? = null
    private val binding get() = _binding!!

    private val chipList = mutableListOf<Chip>()
    private val viewModel: UserViewModel by activityViewModels<UserViewModel>()

    private var selectedInterests = mutableListOf<InterestName>()
    private var isUpdatingChips = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetInterestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnReset.setOnClickListener {
            findNavController().navigateUp()
        }

        val interestsData = arrayOf(
            "Business Analyst",
            "Sports & Wellness",
            "Marketing",
            "Finance",
            "Education & Learning",
            "Science and Technology",
            "Art and Design",
            "Entrepreneurship",
            "Social Impact"
        )
        createChipGroup(interestsData, binding.chipGroup)
        observeInterestsData()
    }

    private fun createChipGroup(interests: Array<String>, chipGroup: ChipGroup) {
        chipList.clear()
        chipGroup.removeAllViews()
        for (interest in interests) {
            val chip = Chip(context).apply {
                text = interest
                isCheckable = true
                chipStrokeWidth = 2f
                chipStrokeColor = ContextCompat.getColorStateList(context, R.color.abu)
                chipBackgroundColor =
                    ContextCompat.getColorStateList(context, R.color.chip_background_color)
                setTextColor(
                    ContextCompat.getColorStateList(
                        context,
                        R.color.chip_background_text_color
                    )
                )

                setOnCheckedChangeListener { _, isChecked ->
                    if (isUpdatingChips) return@setOnCheckedChangeListener

                    if (isChecked) {
                        val newInterest = Interest(
                            name = interest
                        )
                        viewModel.addInterestsData(
                            requireActivity(),
                            UserInterestsRequest(listOf(newInterest))
                        )
                    } else {
                        val matched = selectedInterests.find { it.name == interest}
                        matched?.let {
                            viewModel.deleteInterestsData(requireActivity(), it.id)
                            selectedInterests.remove(it)
                            viewModel.getInterestsData(requireActivity())
                        }
                    }
                }
            }
            chipList.add(chip)
            chipGroup.addView(chip)
        }
        viewModel.getInterestsData(requireActivity())
    }

    private fun markSelectedChips(data: List<String>) {
        isUpdatingChips = true
        for (chip in chipList) {
            if (data.contains(chip.text.toString())) {
                chip.isChecked = true
            }
        }
        isUpdatingChips = false
    }

    private fun observeInterestsData() {
        viewModel.userGetInterestsResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Success -> {
                    val datas = it.data?.data?.interests
                    if (datas != null) {
                        selectedInterests = datas.toMutableList()
                        markSelectedChips(datas.map { interest -> interest.name })
                    }
                }

                is BaseResponse.Error -> {
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

}