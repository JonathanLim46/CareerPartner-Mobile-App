package com.example.careerpartner.main.profile.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.careerpartner.R
import com.example.careerpartner.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAbout.setOnClickListener{
            findNavController().navigate(R.id.action_settingsFragment_to_aboutFragment)
        }

        binding.btnSupportHelp.setOnClickListener{
            findNavController().navigate(R.id.action_settingsFragment_to_supportHelpFragment)
        }

        binding.btnGradientButton.setOnClickListener{
            findNavController().navigate(R.id.action_settingsFragment_to_accountFragment)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}