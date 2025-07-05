package com.example.careerpartner.main.profile.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.careerpartner.R
import com.example.careerpartner.auth.viewmodel.LoginViewModel
import com.example.careerpartner.data.network.BaseResponse
import com.example.careerpartner.data.network.SessionManager
import com.example.careerpartner.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewmodel by viewModels<LoginViewModel>()

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

        viewmodel.logoutResult.observe(requireActivity()) {
            when  (it){
                is BaseResponse.Success -> {
                    SessionManager.clearData(requireActivity())
                    Toast.makeText(requireActivity(), it.data?.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_global_auth_choice)
                }
                is BaseResponse.Error -> {
                    Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_SHORT).show()
                } else -> {
                    Toast.makeText(requireActivity(), "Logout Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

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

        binding.btnLogout.setOnClickListener {
            doLogout()
        }
    }

    private fun doLogout(){
        viewmodel.logout(requireActivity())
    }

}