package com.example.careerpartner.finishup.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.careerpartner.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class FinishUpSkillsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_finish_up_skills, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val skillsOptions = arrayOf(
            "Writing",
            "Design",
            "Editor",
            "Speaking",
            "Science",
            "Marketing"
        )

        val chipGroup = view.findViewById<ChipGroup>(R.id.chipGroup)
        createChipView(skillsOptions, chipGroup)

        view.findViewById<AppCompatButton>(R.id.btnBack).setOnClickListener {
            findNavController().navigateUp()
        }

        view.findViewById<Button>(R.id.btnContinue).setOnClickListener {
            findNavController().navigate(R.id.action_finishUpSkillsFragment_to_finishUpExperienceFragment)
        }
    }

    private fun createChipView(skills: Array<String>, chipGroup: ChipGroup) {
        for (skill in skills){
            val chip = Chip(context).apply {
                text = skill
                isCheckable =  true
                chipStrokeWidth = 2f
                chipStrokeColor = ContextCompat.getColorStateList(context, R.color.abu)
                chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.chip_background_color)
                setTextColor(ContextCompat.getColorStateList(context, R.color.chip_background_text_color))
            }
            chipGroup.addView(chip)
        }
    }
}