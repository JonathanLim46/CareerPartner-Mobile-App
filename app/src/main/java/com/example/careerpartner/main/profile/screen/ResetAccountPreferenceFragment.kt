package com.example.careerpartner.main.profile.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.careerpartner.R
import com.example.careerpartner.databinding.FragmentResetAccountPreferenceBinding

class ResetAccountPreferenceFragment : Fragment() {

    private var _binding: FragmentResetAccountPreferenceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetAccountPreferenceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSkills.setOnClickListener {
            findNavController().navigate(R.id.action_resetAccountPreferenceFragment_to_resetSkillsFragment)
        }

        binding.btnResetInterests.setOnClickListener {
            findNavController().navigate(R.id.action_resetAccountPreferenceFragment_to_resetInterestFragment)
        }
    }
}