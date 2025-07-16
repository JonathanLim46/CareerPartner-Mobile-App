package com.example.careerpartner.main.profile.screen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.careerpartner.R
import com.example.careerpartner.data.model.Skill
import com.example.careerpartner.data.model.SkillName
import com.example.careerpartner.data.model.UserSkillsRequest
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.viewmodel.UserViewModel
import com.example.careerpartner.databinding.FragmentResetSkillsBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ResetSkillsFragment : Fragment() {

    private var _binding: FragmentResetSkillsBinding? = null
    private val binding get() = _binding!!

    private val chipList = mutableListOf<Chip>()
    private val viewModel: UserViewModel by activityViewModels<UserViewModel>()
    private var selectedSkills = mutableListOf<SkillName>()

    private var isUpdatingChips = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetSkillsBinding.inflate(inflater, container, false)
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

        val skillsOptions = arrayOf(
            "Communication",
            "Critical Thinking",
            "Digital Literacy",
            "Teamwork",
            "Time Management",
            "Problem Solving"
        )

        createChipView(skillsOptions, binding.chipGroup)
        observeSkillsData()
    }

    private fun createChipView(skills: Array<String>, chipGroup: ChipGroup) {
        chipList.clear()
        chipGroup.removeAllViews()
        for (skill in skills) {
            val chip = Chip(context).apply {
                text = skill
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

                    if (isChecked){
                        val newSkill = Skill(
                            name = skill
                        )
                        viewModel.addSkillsData(
                            requireActivity(),
                            UserSkillsRequest(listOf(newSkill))
                        )
                    }else{
                        val matched = selectedSkills.find { it.name == skill }
                        matched?.let {
                            viewModel.deleteSkillsData(requireActivity(), it.id)
                            selectedSkills.remove(it)
                            viewModel.getSkillsData(requireActivity())
                        }
                    }
                }
            }
            chipList.add(chip)
            chipGroup.addView(chip)
        }

        viewModel.getSkillsData(requireActivity())
    }

    private fun markSelectedChips(data: List<String>) {
        isUpdatingChips = true
        for (chip in chipList) {
            if (data.contains(chip.text.toString())){
                chip.isChecked = true
            }
        }
        isUpdatingChips = false
    }

    private fun observeSkillsData(){
        viewModel.userGetSkillsResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Success -> {
                    val datas = it.data?.data?.skills
                    if (datas != null){
                        selectedSkills = datas.toMutableList()
                        markSelectedChips(datas.map { skill -> skill.name })
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