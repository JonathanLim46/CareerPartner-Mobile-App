package com.example.careerpartner.finishup.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.careerpartner.R
import com.example.careerpartner.data.model.Interest
import com.example.careerpartner.data.model.UserInterestsRequest
import com.example.careerpartner.finishup.viewmodel.FinishUpViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class FinishUpInterestsFragment : Fragment() {

    private val viewModel: FinishUpViewModel by activityViewModels<FinishUpViewModel>()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_finish_up_interests, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val interestsData = arrayOf(
            "Business Analyst",
            "Developer",
            "Marketing",
            "Finance",
            "Design",
            "Science",
            "Video Editor",
            "Game Dev",
            "Data Engineering"
        )

        val chipGroup = view.findViewById<ChipGroup>(R.id.chipGroup)
        createChipGroup(interestsData, chipGroup)

        view.findViewById<AppCompatButton>(R.id.btnBack).setOnClickListener {
            findNavController().navigateUp()
        }

        view.findViewById<AppCompatButton>(R.id.btnContinue).setOnClickListener {
            val selectedInterests = mutableListOf<Interest>()
            for (i in 0 until chipGroup.childCount) {
                val chip = chipGroup.getChildAt(i) as Chip
                if (chip.isChecked) {
                    selectedInterests.add(Interest(name = chip.text.toString()))
                }
            }

            viewModel.updateInterests(UserInterestsRequest(selectedInterests))

            findNavController().navigate(R.id.action_finishUpInterestsFragment_to_finishUpSkillsFragment)
        }
    }

    private fun createChipGroup(interests: Array<String>, chipGroup: ChipGroup){
        val strokeWidth = 2f
        for(interest in interests){
            val chip = Chip(context).apply {
                text = interest
                isCheckable = true
                chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.chip_background_color)
                setTextColor(ContextCompat.getColorStateList(context, R.color.chip_background_text_color))
                chipStrokeWidth = strokeWidth
                chipStrokeColor = ContextCompat.getColorStateList(context, R.color.abu)
            }
            chipGroup.addView(chip)
        }
    }
}